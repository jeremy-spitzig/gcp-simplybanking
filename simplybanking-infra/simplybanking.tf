provider "google" {
  credentials = "${file("~/.gcp/credentials/terraform-deploy_simplybanking-v4.json")}"
  project     = "simplybankingversion4"
  region      = "southamerica-east1"
}

resource "google_storage_bucket" "simplybanking-artifact-store" {
  name     = "simplybanking-artifact-store-bucket-v4"
  location = "southamerica-east1"
}

resource "google_storage_bucket_acl" "simplybanking-artifact-store-acl" {
  bucket = "${google_storage_bucket.simplybanking-artifact-store.name}"

  role_entity = [
    "READER:allUsers",
  ]
}

resource "google_storage_bucket_object" "api-artifact" {
  name   = "api.jar"
  source = "../simplybanking-api/build/libs/api-2.0.0.jar"
  bucket = "simplybanking-artifact-store-bucket-v4"
}

resource "google_storage_object_acl" "simplybanking-api-artifact-acl" {
  bucket = "${google_storage_bucket.simplybanking-artifact-store.name}"
  object = "${google_storage_bucket_object.api-artifact.output_name}"

  role_entity = [
    "READER:allUsers",
  ]
}

resource "google_storage_bucket_object" "ui-artifact" {
  name   = "ui.tar.gz"
  source = "../simplybanking-ui/dist/simplybanking-ui.tar.gz"
  bucket = "simplybanking-artifact-store-bucket-v4"
}

resource "google_storage_object_acl" "simplybanking-ui-artifact-acl" {
  bucket = "${google_storage_bucket.simplybanking-artifact-store.name}"
  object = "${google_storage_bucket_object.ui-artifact.output_name}"

  role_entity = [
    "READER:allUsers",
  ]
}

resource "google_compute_autoscaler" "simplybanking-api-as" {
  name   = "simplybanking-api-autoscaler"
  zone   = "southamerica-east1-b"
  target = "${google_compute_instance_group_manager.simplybanking-api-igm.self_link}"

  autoscaling_policy {
    max_replicas    = 2
    min_replicas    = 1
    cooldown_period = 60

    cpu_utilization {
      target = 0.5
    }
  }
}

resource "google_compute_instance_template" "simplybanking-api-it" {
  name                        = "simplybanking-api-instance-template"
  machine_type                = "f1-micro"
  can_ip_forward              = false

  tags = ["api", "http-server"]

  metadata_startup_script     = <<EOF
  gsutil cp gs://simplybanking-artifact-store-bucket-v4/api.jar .; 
  apt-get update;
  apt-get install -t stretch-backports -yq openjdk-8-jdk;
  update-alternatives --set java /usr/lib/jvm/java-8-openjdk-amd64/jre/bin/java
  java -jar api.jar --spring.datasource.url=jdbc:postgresql://35.247.215.9:5432/postgres --spring.redis.host=10.67.39.179 --hibernate.dialect=org.hibernate.dialect.PostgreSQL95Dialect --server.port=80
  EOF


  disk {
    source_image = "${data.google_compute_image.debian_9.self_link}"
  }

  network_interface {
    network = "default"
    access_config {
    }
  }

  service_account {
    scopes = ["cloud-platform"]
  }
}

resource "google_compute_target_pool" "simplybanking-api-tp" {
  name = "simplybanking-api-target-pool"
}

resource "google_compute_instance_group_manager" "simplybanking-api-igm" {
  name = "simplybanking-api-igm"
  zone = "southamerica-east1-b"

  instance_template  = "${google_compute_instance_template.simplybanking-api-it.self_link}"

  target_pools       = ["${google_compute_target_pool.simplybanking-api-tp.self_link}"]
  base_instance_name = "simplybanking-api"
}

data "google_compute_image" "debian_9" {
    family  = "debian-9"
    project = "debian-cloud"
}

resource "google_compute_instance" "transaction-processor" {
  name                        = "simplybanking-transaction-processor"
  machine_type                = "f1-micro"
  zone                        = "southamerica-east1-b"
  tags                        = ["api"]
  allow_stopping_for_update   = true

  boot_disk {
      initialize_params {
          image = "debian-cloud/debian-9"
      }
  }

  network_interface {
    network = "default"
    access_config {
    }

  }

  service_account {
    scopes = ["cloud-platform"]
  }
}

resource "google_compute_instance" "transaction-ui" {
  name                        = "simplybanking-ui"
  machine_type                = "f1-micro"
  zone                        = "southamerica-east1-b"
  tags                        = ["ui", "http-server"]
  allow_stopping_for_update   = true

  metadata_startup_script     = <<EOF
  gsutil cp gs://simplybanking-artifact-store-bucket-v4/ui.tar.gz .;
  tar -xvzf ui.tar.gz;
  apt-get update;
  apt-get install -yq apache2;
  cp -R simplybanking-ui/* /var/www/html/
  EOF

  boot_disk {
      initialize_params {
          image = "debian-cloud/debian-9"
      }
  }

  network_interface {
    network = "default"
    access_config {
    }

  }

  service_account {
    scopes = ["cloud-platform"]
  }
}

#resource "google_compute_firewall" "allow-traffic-to-data-services" {
#  name    = "allow-traffic-to-data-services"
#  network = "default"
#
#  allow {
#    protocol = "tcp"
#    ports    = ["5432", "27017", "6379"]
#  }

#  source_tags = ["api"]
#  target_tags = ["data-services"]
#}



resource "google_compute_firewall" "allow-traffic-to-http-servers" {
  name    = "allow-traffic-to-http-servers"
  network = "default"

  allow {
    protocol = "tcp"
    ports    = ["80"]
  }

  source_ranges = ["0.0.0.0/0"]
  target_tags = ["http-server"]
}
