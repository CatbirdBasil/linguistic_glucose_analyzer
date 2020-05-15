drop table if exists "User";

drop table if exists Glucose_Data_Record;

drop table if exists Glucose_Data_Record_Type;

drop table if exists Prediction_Chain_Occasion;

drop table if exists Alphabet;

drop table if exists Distribution;

drop table if exists Person;

drop table if exists Diabetes_Type;


/*==============================================================*/
/* Table: Alphabet                                              */
/*==============================================================*/
create table Alphabet (
   id                   INT4                 not null,
   name                 CHAR(64)             not null,
   symbols              VARCHAR(512)         not null,
   constraint PK_ALPHABET primary key (id)
);

/*==============================================================*/
/* Table: Diabities_Type                                        */
/*==============================================================*/
create table Diabetes_Type (
   id                   INT4                 not null,
   diabetes_type_name  CHAR(32)             not null,
   constraint PK_DIABETES_TYPE primary key (id)
);

/*==============================================================*/
/* Table: Distribution                                          */
/*==============================================================*/
create table Distribution (
   id                   INT4                 not null,
   name                 CHAR(128)            not null,
   constraint PK_DISTRIBUTION primary key (id)
);

/*==============================================================*/
/* Table: Glucose_Data_Record                                   */
/*==============================================================*/
create table Glucose_Data_Record (
   id                   SERIAL,
   person_id            INT4                 not null,
   record_time          DATE                 not null,
   record_type_id       INT4                 not null,
   value                INT4                 not null,
   constraint PK_GLUCOSE_DATA_RECORD primary key (id)
);

/*==============================================================*/
/* Table: Glucose_Data_Record_Type                              */
/*==============================================================*/
create table Glucose_Data_Record_Type (
   id                   INT4                 not null,
   record_type_name     CHAR(128)            not null,
   constraint PK_GLUCOSE_DATA_RECORD_TYPE primary key (id)
);

/*==============================================================*/
/* Table: Person                                                */
/*==============================================================*/
create table Person (
   id                   SERIAL,
   diabetes_type_id    INT4                 not null,
   name                 VARCHAR(64)          null,
   surname              VARCHAR(128)         null,
   birth_date           DATE                 null,
   constraint PK_PERSON primary key (id)
);

/*==============================================================*/
/* Table: PredictionChainOccasion                               */
/*==============================================================*/
create table Prediction_Chain_Occasion (
   id                   SERIAL,
   alphabet_id          INT4                 not null,
   person_id            INT4                 not null,
   distribution_id      INT4                 not null,
   linguistic_chain     VARCHAR(512)         not null,
   possible_result      VARCHAR(16)          not null,
   occasions            INT4                 not null,
   constraint PK_PREDICTIONCHAINOCCASION primary key (id)
);

/*==============================================================*/
/* Table: "User"                                                */
/*==============================================================*/
create table "User" (
   id                   SERIAL,
   person_id            INT4                 not null,
   login                CHAR(64)             not null,
   password             CHAR(256)            not null,
   salt                 CHAR(64)             not null,
   email                CHAR(256)            not null,
   registration_date    DATE                 not null,
   is_email_verified    BOOL                 not null,
   constraint PK_USER primary key (id)
);

alter table Glucose_Data_Record
   add constraint FK_RECORD_TYPE foreign key (record_type_id)
      references Glucose_Data_Record_Type (id)
      on delete restrict on update restrict;

alter table Glucose_Data_Record
   add constraint FK_RECORD__PERSON foreign key (person_id)
      references Person (id)
      on delete restrict on update restrict;

alter table Person
   add constraint FK_PERSON_DTYPE foreign key (diabetes_type_id)
      references Diabetes_Type (id)
      on delete restrict on update restrict;

alter table Prediction_Chain_Occasion
   add constraint FK_PRED_DISTR foreign key (distribution_id)
      references Distribution (id)
      on delete restrict on update restrict;

alter table Prediction_Chain_Occasion
   add constraint FK_PRED_ALPHAB foreign key (alphabet_id)
      references Alphabet (id)
      on delete restrict on update restrict;

alter table Prediction_Chain_Occasion
   add constraint FK_PRED_PERSON foreign key (person_id)
      references Person (id)
      on delete restrict on update restrict;

alter table "User"
   add constraint FK_USER_PERSON foreign key (person_id)
      references Person (id)
      on delete restrict on update restrict;

----------------------------
--- DICTIONARY INSERTS -----
----------------------------

insert into Alphabet(id, name, symbols)
values (1, 'English', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ');
insert into Alphabet(id, name, symbols)
values (2, 'ASCII100', '100 ASCII Symbols');

insert into Distribution(id, name)
values (1, 'Uniform');
insert into Distribution(id, name)
values (2, 'LogNormal');
insert into Distribution(id, name)
values (3, 'Parabolic');

insert into Diabetes_Type(id, diabetes_type_name)
values (1, 'First type');
insert into Diabetes_Type(id, diabetes_type_name)
values (2, 'Second type');
insert into Diabetes_Type(id, diabetes_type_name)
values (3, 'No diabetes');

insert into Glucose_Data_Record_Type(id, record_type_name)
values (48, 'UNSPECIFIED_BLOOD_GLUCOSE_MEASUREMENT');
insert into Glucose_Data_Record_Type(id, record_type_name)
values (58, 'PRE_BREAKFAST_BLOOD_GLUCOSE_MEASUREMENT');
insert into Glucose_Data_Record_Type(id, record_type_name)
values (59, 'POST_BREAKFAST_BLOOD_GLUCOSE_MEASUREMENT');
insert into Glucose_Data_Record_Type(id, record_type_name)
values (62, 'PRE_SUPPER_BLOOD_GLUCOSE_MEASUREMENT');
insert into Glucose_Data_Record_Type(id, record_type_name)
values (63, 'POST_SUPPER_BLOOD_GLUCOSE_MEASUREMENT');
insert into Glucose_Data_Record_Type(id, record_type_name)
values (64, 'PRE_SNACK_BLOOD_GLUCOSE_MEASUREMENT');
insert into Glucose_Data_Record_Type(id, record_type_name)
values (65, 'HYPOGLYCEMIC_SYMPTOMS');
insert into Glucose_Data_Record_Type(id, record_type_name)
values (66, 'TYPICAL_MEAL_INGESTION');
insert into Glucose_Data_Record_Type(id, record_type_name)
values (67, 'MORE_THAN_USUAL_MEAL_INGESTION');
insert into Glucose_Data_Record_Type(id, record_type_name)
values (68, 'LESS_THAN_USUAL_MEAL_INGESTION');
insert into Glucose_Data_Record_Type(id, record_type_name)
values (72, 'UNSPECIFIED_SPECIAL_EVENT');