create table if not exists parent_assist.flyway_schema_history
(
    installed_rank integer                 not null
        constraint flyway_schema_history_pk
            primary key,
    version        varchar(50),
    description    varchar(200)            not null,
    type           varchar(20)             not null,
    script         varchar(1000)           not null,
    checksum       integer,
    installed_by   varchar(100)            not null,
    installed_on   timestamp default now() not null,
    execution_time integer                 not null,
    success        boolean                 not null
);

alter table parent_assist.flyway_schema_history
    owner to parent_assist;

create index if not exists flyway_schema_history_s_idx
    on parent_assist.flyway_schema_history (success);

create table if not exists parent_assist.activities
(
    id          varchar(255) not null
        primary key,
    age_from    integer,
    age_to      integer,
    age_unit    varchar(255),
    description text         not null,
    how_to      text,
    name        varchar(255) not null,
    rules       text         not null,
    time        varchar(255)
);

alter table parent_assist.activities
    owner to parent_assist;

create table if not exists parent_assist.children
(
    id            varchar(255) not null
        primary key,
    date_of_birth date         not null,
    gender        varchar(255) not null,
    image_url     varchar(255),
    name          varchar(255) not null
);

alter table parent_assist.children
    owner to parent_assist;

create table if not exists parent_assist.event_activity
(
    id             varchar(255) not null
        primary key,
    date           date         not null,
    dificulty      varchar(255) not null,
    feedback       text,
    interest       varchar(255) not null,
    activity_id_id varchar(255) not null
        constraint fkb49a1n73q8ugq2vqv30fbp1cj
            references parent_assist.activities,
    child_id_id    varchar(255) not null
        constraint fk2vdb1hs9ts28qs439xnwwmvic
            references parent_assist.children
);

alter table parent_assist.event_activity
    owner to parent_assist;

create table if not exists parent_assist.materials_toys
(
    id          varchar(255) not null
        primary key,
    category    varchar(255),
    description text,
    image_url   varchar(255),
    name        varchar(255)
);

alter table parent_assist.materials_toys
    owner to parent_assist;

create table if not exists parent_assist.activity_materials
(
    activities_id     varchar(255) not null
        constraint fk6dv2pcp8xoqv0x7nf5eeirppt
            references parent_assist.activities,
    materials_toys_id varchar(255) not null
        constraint fkkjk8p7uuu9exffgqm37loerjw
            references parent_assist.materials_toys,
    primary key (activities_id, materials_toys_id)
);

alter table parent_assist.activity_materials
    owner to parent_assist;

create table if not exists parent_assist.milestones
(
    id          varchar(255) not null
        primary key,
    age_from    integer,
    age_to      integer,
    age_unit    varchar(255),
    description text,
    name        varchar(255)
);

alter table parent_assist.milestones
    owner to parent_assist;

create table if not exists parent_assist.activities_milestones
(
    activities_id varchar(255) not null
        constraint fk2uocpeo4kaul1426nnq90g6vl
            references parent_assist.activities,
    milestones_id varchar(255) not null
        constraint fkkc2e7hvyt0c16n8clbrhk8b1d
            references parent_assist.milestones,
    primary key (activities_id, milestones_id)
);

alter table parent_assist.activities_milestones
    owner to parent_assist;

create table if not exists parent_assist.event_milestone
(
    id              varchar(255) not null
        primary key,
    date            date         not null,
    child_id_id     varchar(255)
        constraint fk6u41y230sb8gb96hva1bhcpg3
            references parent_assist.children,
    milestone_id_id varchar(255)
        constraint fkmuqfkvxfm68f5kh5gge1ludtw
            references parent_assist.milestones
);

alter table parent_assist.event_milestone
    owner to parent_assist;

create table if not exists parent_assist.parents
(
    id            varchar(255) not null
        primary key,
    address       varchar(255),
    city          varchar(255),
    country       varchar(255),
    date_of_birth date,
    language      varchar(255),
    name          varchar(100) not null,
    postal_code   varchar(255),
    surname       varchar(100)
);

alter table parent_assist.parents
    owner to parent_assist;

create table if not exists parent_assist.place
(
    id   varchar(255) not null
        primary key,
    name varchar(255)
);

alter table parent_assist.place
    owner to parent_assist;

create table if not exists parent_assist.activity_places
(
    activities_id varchar(255) not null
        constraint fk12a25h0rec6j6ujop39dcfbsm
            references parent_assist.activities,
    place_id      varchar(255) not null
        constraint fk7108f4fjpx2l1t78fuuicg38y
            references parent_assist.place,
    primary key (activities_id, place_id)
);

alter table parent_assist.activity_places
    owner to parent_assist;

create table if not exists parent_assist.product
(
    id          varchar(255) not null
        primary key,
    create_date date,
    description varchar(255),
    name        varchar(255)
);

alter table parent_assist.product
    owner to parent_assist;

create table if not exists parent_assist.plan
(
    id            varchar(255) not null
        primary key,
    interval      varchar(255),
    name          varchar(255),
    product_id_id varchar(255) not null
        constraint fkn5vvsyw2f3bwklouv3tw934ax
            references parent_assist.product
);

alter table parent_assist.plan
    owner to parent_assist;

create table if not exists parent_assist.plan_pricing
(
    id          varchar(255)     not null
        primary key,
    currency    varchar(255)     not null,
    ending_at   timestamp(6),
    price       double precision not null,
    starting_at timestamp(6)     not null,
    plan_id     varchar(255)     not null
        constraint fk4v6bjs44ichkrt3geoknu5uow
            references parent_assist.plan
);

alter table parent_assist.plan_pricing
    owner to parent_assist;

create table if not exists parent_assist.questions
(
    id          varchar(255) not null
        primary key,
    age_from    varchar(255) not null,
    age_to      varchar(255) not null,
    age_unit    varchar(255) not null,
    answer      varchar(255),
    description varchar(255),
    name        varchar(255) not null,
    option1     varchar(255) not null,
    option2     varchar(255) not null,
    option3     varchar(255),
    option4     varchar(255),
    option5     varchar(255)
);

alter table parent_assist.questions
    owner to parent_assist;

create table if not exists parent_assist.relationships
(
    id           varchar(255) not null
        primary key,
    relationship varchar(255) not null,
    child_id_id  varchar(255)
        constraint fk2edyvwj9vnkkfd6eo5jd8uw5s
            references parent_assist.children,
    parent_id_id varchar(255) not null
        constraint fkebqvaurkxv93o15c3w7wdi0vc
            references parent_assist.parents,
    plan_id      varchar(255) not null
        constraint fkfki7sq5pojoh7c5d6niep6xfn
            references parent_assist.plan
);

alter table parent_assist.relationships
    owner to parent_assist;

create table if not exists parent_assist.roles
(
    id   varchar(255) not null
        primary key,
    name varchar(255)
);

alter table parent_assist.roles
    owner to parent_assist;

create table if not exists parent_assist.schedule_activities
(
    id             varchar(255) not null
        primary key,
    period_from    date,
    period_to      date,
    activity_id_id varchar(255)
        constraint fknsxrbpw19rfs38lmaslrjuus0
            references parent_assist.activities
);

alter table parent_assist.schedule_activities
    owner to parent_assist;

create table if not exists parent_assist.skills
(
    id          varchar(255) not null
        primary key,
    description varchar(255),
    name        varchar(255)
);

alter table parent_assist.skills
    owner to parent_assist;

create table if not exists parent_assist.activity_skills
(
    activities_id varchar(255) not null
        constraint fk1unrur9d2xnm387wq9w0020or
            references parent_assist.activities,
    skills_id     varchar(255) not null
        constraint fk9g8s2qeu8mhwmyune69i53c4e
            references parent_assist.skills,
    primary key (activities_id, skills_id)
);

alter table parent_assist.activity_skills
    owner to parent_assist;

create table if not exists parent_assist.milestone_skills
(
    milestones_id varchar(255) not null
        constraint fknmoesg8k2w9rn4o0hkkb3fs9l
            references parent_assist.milestones,
    skills_id     varchar(255) not null
        constraint fki3ccpglphkevy068wkvheidff
            references parent_assist.skills,
    primary key (milestones_id, skills_id)
);

alter table parent_assist.milestone_skills
    owner to parent_assist;

create table if not exists parent_assist.subscription
(
    id          varchar(255) not null
        primary key,
    "end"       timestamp(6),
    start       timestamp(6),
    child_id_id varchar(255) not null
        constraint fk4wi7u50jbke5ul1oj6rwe9kgw
            references parent_assist.children
);

alter table parent_assist.subscription
    owner to parent_assist;

create table if not exists parent_assist.purchase
(
    id                 varchar(255)     not null
        primary key,
    amount             double precision not null,
    date               timestamp(6)     not null,
    payment_method     varchar(255)     not null,
    subscription_id_id varchar(255)     not null
        constraint fknp3m4i7yp37b0fdrn5udc1834
            references parent_assist.subscription
);

alter table parent_assist.purchase
    owner to parent_assist;

create table if not exists parent_assist.test
(
    id   varchar(255) not null
        primary key,
    name varchar(255)
);

alter table parent_assist.test
    owner to parent_assist;

create table if not exists parent_assist.event_test
(
    id          bigint       not null
        primary key,
    date        timestamp(6),
    option      varchar(255),
    child_id_id varchar(255) not null
        constraint fk135s40ksmj5cu85lab3p8cus8
            references parent_assist.children,
    test_id_id  varchar(255) not null
        constraint fk9n87mj6j9uos9mabr7ab0q47a
            references parent_assist.test
);

alter table parent_assist.event_test
    owner to parent_assist;

create table if not exists parent_assist.test_question
(
    questions_id varchar(255) not null
        constraint fkqye68h3njtjl0h3ek4apno168
            references parent_assist.questions,
    test_id      varchar(255) not null
        constraint fkk2sfq1wyx19uvwn7pkgk1bc9n
            references parent_assist.test,
    primary key (questions_id, test_id)
);

alter table parent_assist.test_question
    owner to parent_assist;

create table if not exists parent_assist.users
(
    id           varchar(255) not null
        primary key,
    email        varchar(50)  not null,
    parent       varchar(255),
    password     varchar(120) not null,
    personinfo   varchar(255),
    username     varchar(35)  not null,
    parent_id_id varchar(255) not null
        constraint fkk7kgc7ly7v93nj87ypkcdqvv5
            references parent_assist.parents
);

alter table parent_assist.users
    owner to parent_assist;

create table if not exists parent_assist.user_roles
(
    users_id varchar(255) not null
        constraint fkoovdgg7vvr1hb8vw6ivcrv3tb
            references parent_assist.users,
    roles_id varchar(255) not null
        constraint fkdbv8tdyltxa1qjmfnj9oboxse
            references parent_assist.roles,
    primary key (users_id, roles_id)
);

alter table parent_assist.user_roles
    owner to parent_assist;
