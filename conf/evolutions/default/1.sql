# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table apartment (
  id                        bigint not null,
  apartment_no              varchar(255),
  apartment_building_id     bigint,
  constraint pk_apartment primary key (id))
;

create table apartment_building (
  id                        bigint not null,
  name                      varchar(255),
  email                     varchar(255),
  phone                     varchar(255),
  address                   varchar(255),
  real_estate_company_id    bigint,
  constraint pk_apartment_building primary key (id))
;

create table bill (
  id                        bigint not null,
  description               varchar(255),
  date_of_issuing           time,
  deadline                  time,
  status                    varchar(255),
  amount                    double,
  constraint pk_bill primary key (id))
;

create table bill_notification (
  issue_date                date,
  status                    varchar(6),
  bill_id                   bigint,
  constraint ck_bill_notification_status check (status in ('Read','Unread')),
  constraint pk_bill_notification primary key (issue_date, status))
;

create table maintenance_task (
  id                        bigint not null,
  task_type                 varchar(255),
  description               varchar(255),
  status                    varchar(255),
  deadline                  time,
  constraint pk_maintenance_task primary key (id))
;

create table maintenance_task_notification (
  issue_date                date,
  status                    varchar(6),
  maintenance_task_id       bigint,
  constraint ck_maintenance_task_notification_status check (status in ('Read','Unread')),
  constraint pk_maintenance_task_notification primary key (issue_date, status))
;

create table message (
  internal_id               bigint not null,
  THREAD_ID                 bigint not null,
  time_stamp                time,
  body                      varchar(255),
  is_read                   boolean,
  constraint pk_message primary key (internal_id))
;

create table notice (
  internal_id               bigint not null,
  category                  varchar(255),
  subject                   varchar(255),
  publish_date              date,
  valid_until               date,
  description               varchar(255),
  published_by              bigint,
  viewcount                 integer,
  constraint pk_notice primary key (internal_id))
;

create table notification (
  id                        bigint not null,
  receiver_id               bigint,
  issue_date                date,
  status                    varchar(6),
  constraint ck_notification_status check (status in ('Read','Unread')),
  constraint pk_notification primary key (id))
;

create table real_estate_company (
  id                        bigint not null,
  name                      varchar(255),
  email                     varchar(255),
  phone                     varchar(255),
  address                   varchar(255),
  constraint pk_real_estate_company primary key (id))
;

create table thread (
  internal_id               bigint not null,
  category                  varchar(255),
  date                      date,
  subject                   varchar(255),
  sender_id                 bigint,
  receiver_id               bigint,
  constraint pk_thread primary key (internal_id))
;

create table user_account (
  id                        bigint not null,
  first_name                varchar(255),
  last_name                 varchar(255),
  email                     varchar(255),
  phone                     varchar(255),
  account_type              varchar(10),
  join_date                 date,
  apartment_id              bigint,
  constraint ck_user_account_account_type check (account_type in ('Resident','Manager','Supervisor')),
  constraint pk_user_account primary key (id))
;

create sequence apartment_seq;

create sequence apartment_building_seq;

create sequence bill_seq;

create sequence bill_notification_seq;

create sequence maintenance_task_seq;

create sequence maintenance_task_notification_seq;

create sequence message_seq;

create sequence notice_seq;

create sequence notification_seq;

create sequence real_estate_company_seq;

create sequence thread_seq;

create sequence user_account_seq;

alter table apartment add constraint fk_apartment_apartmentBuilding_1 foreign key (apartment_building_id) references apartment_building (id) on delete restrict on update restrict;
create index ix_apartment_apartmentBuilding_1 on apartment (apartment_building_id);
alter table apartment_building add constraint fk_apartment_building_realEsta_2 foreign key (real_estate_company_id) references real_estate_company (id) on delete restrict on update restrict;
create index ix_apartment_building_realEsta_2 on apartment_building (real_estate_company_id);
alter table bill_notification add constraint fk_bill_notification_bill_3 foreign key (bill_id) references bill (id) on delete restrict on update restrict;
create index ix_bill_notification_bill_3 on bill_notification (bill_id);
alter table maintenance_task_notification add constraint fk_maintenance_task_notificati_4 foreign key (maintenance_task_id) references maintenance_task (id) on delete restrict on update restrict;
create index ix_maintenance_task_notificati_4 on maintenance_task_notification (maintenance_task_id);
alter table message add constraint fk_message_thread_5 foreign key (THREAD_ID) references thread (internal_id) on delete restrict on update restrict;
create index ix_message_thread_5 on message (THREAD_ID);
alter table notification add constraint fk_notification_receiver_6 foreign key (receiver_id) references user_account (id) on delete restrict on update restrict;
create index ix_notification_receiver_6 on notification (receiver_id);
alter table user_account add constraint fk_user_account_apartment_7 foreign key (apartment_id) references apartment (id) on delete restrict on update restrict;
create index ix_user_account_apartment_7 on user_account (apartment_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists apartment;

drop table if exists apartment_building;

drop table if exists bill;

drop table if exists bill_notification;

drop table if exists maintenance_task;

drop table if exists maintenance_task_notification;

drop table if exists message;

drop table if exists notice;

drop table if exists notification;

drop table if exists real_estate_company;

drop table if exists thread;

drop table if exists user_account;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists apartment_seq;

drop sequence if exists apartment_building_seq;

drop sequence if exists bill_seq;

drop sequence if exists bill_notification_seq;

drop sequence if exists maintenance_task_seq;

drop sequence if exists maintenance_task_notification_seq;

drop sequence if exists message_seq;

drop sequence if exists notice_seq;

drop sequence if exists notification_seq;

drop sequence if exists real_estate_company_seq;

drop sequence if exists thread_seq;

drop sequence if exists user_account_seq;

