DROP TABLE IF EXISTS `MEMBER`;

CREATE TABLE `MEMBER` (
                          `seq_id`	int	 NOT NULL,
                          `id`	varchar(30)	NOT NULL,
                          `pw`	varchar(20)	NOT NULL,
                          `nickname`	varchar(20)	NULL


);
ALTER TABLE `MEMBER` ALTER COLUMN `seq_id` INT NOT NULL AUTO_INCREMENT;
ALTER TABLE `MEMBER` ADD CONSTRAINT `PK_MEMBER` PRIMARY KEY (`seq_id`);
ALTER TABLE MEMBER ADD CONSTRAINT UNIQUE_MEMBER UNIQUE (ID);

DROP TABLE IF EXISTS `Scheduler`;

CREATE TABLE `Scheduler` (
                             `scheduler_seq`	int	NOT NULL,
                             `date`	DATE	NOT NULL,
                             `member_seq_id`	int	NOT NULL
);

ALTER TABLE `Scheduler` ALTER COLUMN `scheduler_seq` INT NOT NULL AUTO_INCREMENT;
ALTER TABLE `Scheduler` ADD CONSTRAINT `PK_SCHEDULER` PRIMARY KEY (`scheduler_seq`);

DROP TABLE IF EXISTS `TODO`;

CREATE TABLE `TODO` (
                        `todo_id`	BIGINT	NOT NULL,
                        `scheduler_seq`	int	NOT NULL,
                        `content`	varchar(255)	NOT NULL,
                        `completed` BOOLEAN	NOT NULL	COMMENT 'T : 투두 체크 된 상태/F : 투두 체크 X 상태'
);

ALTER TABLE `TODO` ALTER COLUMN `todo_id` INT NOT NULL AUTO_INCREMENT;
ALTER TABLE `TODO` ADD CONSTRAINT `PK_TODO` PRIMARY KEY (`todo_id`);

DROP TABLE IF EXISTS `STUDY_TIME`;

CREATE TABLE `STUDY_TIME` (
                              `date`	char(8)	NOT NULL,
                              `seq_id`	int	NOT NULL,
                              `id`	varchar(30)	NOT NULL,
                              `status`	char(1)	NULL	COMMENT 'T: 활성화/F:종료(하루가 지나면 종료 되도록 업데이트)'
);

DROP TABLE IF EXISTS `STUDY_ROOM`;

CREATE TABLE `STUDY_ROOM` (
                              `room_id`	long NOT NULL,
                              `room_name`	varchar(20)	NOT NULL,
                              `max_participants`	int	NOT NULL,
                              `current_participants` int NOT NULL,
                              `create_date`	DATETIME NOT NULL,
                              `activated`	BOOLEAN	NOT NULL	COMMENT 'T:활성화/F:종료',
                              `manager_seq_id`	int	NOT NULL
);
ALTER TABLE `STUDY_ROOM` ALTER COLUMN `room_id` INT NOT NULL AUTO_INCREMENT;
ALTER TABLE `STUDY_ROOM` ADD CONSTRAINT `PK_STUDYROOM` PRIMARY KEY (`room_id`);

DROP TABLE IF EXISTS `PARTICIPANT`;

CREATE TABLE `PARTICIPANT` (
                               `room_id`	int	NOT NULL,
                               `seq_id`	int	NOT NULL,
                               `role` varchar(20) NOT NULL ,
                               `entry_time` DATETIME NOT NULL
);

