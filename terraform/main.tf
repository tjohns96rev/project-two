# the name provided in the provider section (see the word in quotation marks) needs to match the name of the required
# provider in our terraform.tf file, where we defined our required_provder
provider "aws" {
  region     = "us-east-1" # this tells terraform where we want our resources created
  access_key = var.access_key
  secret_key = var.secret_key
}

module "planetarium-rds" { # you can see more of this module's info in the .terraform/modules directory
  source = "terraform-aws-modules/rds/aws"

  identifier = "planetarium-terraform"

  engine            = "postgres"
  engine_version    = "13.7"
  instance_class    = "db.t3.micro"
  allocated_storage = 20

  db_name  = "planetariumTerraform"
  username = "TeamRocket"
  port     = "5432"

  iam_database_authentication_enabled = true

  vpc_security_group_ids = ["sg-06aa407bd5950adb9"]

  #   maintenance_window = "Mon:00:00-Mon:03:00"
  #   backup_window      = "03:00-06:00"

  # Enhanced Monitoring - see example for details on how to create the role
  # by yourself, in case you don't want to create it automatically
  #   monitoring_interval    = "7"
  #   monitoring_role_name   = "MyRDSMonitoringRole"
  #   create_monitoring_role = false

  tags = {
    Owner       = "TeamRocket"
    Environment = "dev"
    terraform   = "true"
  }

  # DB subnet group
  create_db_subnet_group = true
  subnet_ids             = ["subnet-02c6416585f5eeb02", "subnet-05882bb606a1254b0", "subnet-05882bb606a1254b0", "subnet-02810703012333faa", "subnet-0a299e9e14f09e6b6", "subnet-03d5c3311ae8e222f"]

  # Database Deletion Protection
  deletion_protection = true

  family = "postgres13"

  # DB option group
  major_engine_version = "13"

  #   parameters = [
  #     {
  #       name  = "character_set_client"
  #       value = "utf8mb4"
  #     },
  #     {
  #       name  = "character_set_server"
  #       value = "utf8mb4"
  #     }
  #   ]

  #   options = [
  #     {
  #       option_name = "MARIADB_AUDIT_PLUGIN"

  #       option_settings = [
  #         {
  #           name  = "SERVER_AUDIT_EVENTS"
  #           value = "CONNECT"
  #         },
  #         {
  #           name  = "SERVER_AUDIT_FILE_ROTATIONS"
  #           value = "37"
  #         },
  #       ]
  #     },
  #   ]
}

resource "aws_s3_bucket" "terraform_state" {
  # if you get a "wrong region" error there is a good chance it is because of your bucket name
  bucket = "revature-planetarium-bucket" # this bucket name has already been taken, leaving it for now to show error message
  lifecycle {
    prevent_destroy = true # this makes sure terraform does not destroy the bucket
  }
  versioning {
    enabled = true # this makes the bucket keep track of the various objects placed inside
  }
  server_side_encryption_configuration {
    rule {
      apply_server_side_encryption_by_default {
        sse_algorithm = "AES256"
      }
    }
  }

}
