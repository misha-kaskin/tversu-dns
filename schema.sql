create table if not exists users (
    id bigint generated by default as identity primary key,
    login varchar(20),
    password varchar(20)
);

create table if not exists Gpu (
    id bigint generated by default as identity primary key,
    model varchar(40),
    cost int,
    manufacture varchar(20),
    graphicProcessor varchar(20),
    memoryCapacity int,
    manufactureOfProcessor varchar(20),
    typeOfMemory varchar(20),
    connectionInterface varchar(20)
);

create table if not exists Cpu (
    id bigint generated by default as identity primary key,
    model varchar(40),
    cost int,
    manufacture varchar(20),
    socket varchar(20),
    family varchar(20),
    numberOfCores int,
    year int,
    typeOfMemory varchar(20),
    frequency float,
    technicalProcess int
);

create table if not exists Ram (
    id bigint generated by default as identity primary key,
    model varchar(40),
    cost int,
    manufacture varchar(20),
    typeOfMemory varchar(20),
    memoryCapacity int,
    frequency float,
    numberOfModules int
);