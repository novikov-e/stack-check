drop table tasks, task_lists, columns, projects, users;

create table users (
    user_id uuid default gen_random_uuid() primary key,
    firstname varchar(50),
    lastname varchar(50),
    date_of_birth date,
    sex varchar(1),
    image varchar(150),
    email varchar(100) unique not null,
    password varchar(100) not null);

create table projects (
    project_id uuid default gen_random_uuid() primary key,
    user_id uuid references users (user_id),
    project_name varchar(100) not null,
    project_position integer);

create table columns (
    column_id uuid default gen_random_uuid() primary key,
    project_id uuid references projects (project_id),
    column_name varchar(80) not null,
    column_position integer);

create table task_lists (
    task_list_id uuid default gen_random_uuid() primary key,
    column_id uuid references columns (column_id),
    task_list_name varchar(150) not null,
    task_list_position integer,
    task_list_color varchar(7) default '#e9e5e3');

create table tasks (
    task_id uuid default gen_random_uuid() primary key,
    task_list_id uuid references task_lists (task_list_id),
    task_name varchar(100) not null,
    task_state boolean default false,
    task_position integer);