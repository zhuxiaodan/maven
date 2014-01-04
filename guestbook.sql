create database if not exists `d9a5c62f8bf9c42deb0bf941516b39906`;
use d9a5c62f8bf9c42deb0bf941516b39906;


create table users(
    username    VARCHAR(20),
    password    VARCHAR(16)     NOT NULL,
    email       VARCHAR(50)     NOT NULL,
    style       VARCHAR(10)     NOT NULL,
    PRIMARY KEY(username)
);

create table managers(
    username    VARCHAR(20),
    password    VARCHAR(16)     NOT NULL,
    PRIMARY KEY(username)
);

create table gst(
    gst_id      INT             AUTO_INCREMENT,
    gst_user    VARCHAR(20)     NOT NULL,
    gst_title   VARCHAR(100)    NOT NULL,
    gst_content TEXT,
    gst_time    TIMESTAMP       NOT NULL,
    gst_ip      VARCHAR(15)     NOT NULL,
    PRIMARY KEY(gst_id),
    CONSTRAINT FK_USER FOREIGN KEY (gst_user) REFERENCES users(username)
);

insert into managers values('sunxin','1234');