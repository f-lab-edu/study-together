DROP TABLE IF EXISTS `MEMBERS`;

CREATE TABLE `MEMBER` (
                          `seq_id`	int	NOT NULL,
                          `id`	varchar(30)	NOT NULL,
                          `pw`	varchar(20)	NOT NULL,
                          `nickname`	varchar(20)	NULL
);

DROP TABLE IF EXISTS `Scheduler`;

CREATE TABLE `Scheduler` (
                             `scheduler_seq`	int	NOT NULL,
                             `date`	char(8)	NOT NULL,
                             `seq_id`	int	NOT NULL,
                             `id`	varchar(30)	NOT NULL
);

DROP TABLE IF EXISTS `TODO`;

CREATE TABLE `TODO` (
                        `todo_seq`	int	NOT NULL,
                        `scheduler_seq`	int	NOT NULL,
                        `seq_id`	int	NOT NULL,
                        `id`	varchar(30)	NOT NULL,
                        `date`	char(8)	NOT NULL,
                        `todo`	varchar(255)	NOT NULL,
                        `check`	char(1)	NOT NULL	COMMENT 'T : 투두 체크 된 상태/F : 투두 체크 X 상태'
);

DROP TABLE IF EXISTS `STUDY_TIME`;

CREATE TABLE `STUDY_TIME` (
                              `date`	char(8)	NOT NULL,
                              `seq_id`	int	NOT NULL,
                              `id`	varchar(30)	NOT NULL,
                              `status`	char(1)	NULL	COMMENT 'T: 활성화/F:종료(하루가 지나면 종료 되도록 업데이트)'
);

DROP TABLE IF EXISTS `STUDY_ROOM`;

CREATE TABLE `STUDY_ROOM` (
                              `room_id`	int	NOT NULL,
                              `room_name`	varchar(20)	NOT NULL,
                              `total`	int	NOT NULL,
                              `participants_num`	int	NOT NULL,
                              `create_date`	char(8)	NOT NULL,
                              `status`	char(1)	NOT NULL	COMMENT 'T: 활성화/F:종료',
                              `manager_seq_id`	int	NOT NULL
);

DROP TABLE IF EXISTS `PARTICIPANT`;

CREATE TABLE `PARTICIPANT` (
                               `room_id`	int	NOT NULL,
                               `seq_id`	int	NOT NULL,
                               `id`	varchar(30)	NOT NULL
);

