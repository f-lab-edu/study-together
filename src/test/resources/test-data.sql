INSERT INTO MEMBER (id, pw, nickname)
VALUES
    ('john_doe', 'password123', 'John'),
    ('jane_doe', 'qwerty456', 'Jane'),
    ('bob_smith', 'abc123', 'Bob'),
    ('alice_wonderland', 'p@ssw0rd!', 'Alice');


INSERT INTO STUDY_ROOM (room_name, max_participants, current_participants, create_date, activated, manager_seq_id)
VALUES
    ('Room 1', 10, 5, '2024-04-30', TRUE, 1),
    ('Room 2', 8, 3, '2024-04-29', FALSE, 2),
    ('Room 3', 12, 7, '2024-04-28', TRUE, 3),
    ('Room 4', 6, 2, '2024-04-27', FALSE, 4);

INSERT INTO PARTICIPANT (room_id, seq_id, role, entry_time)
VALUES (1, 1, 'Study Room Manager', '2024-07-19 10:00:00');

INSERT INTO PARTICIPANT (room_id, seq_id, role, entry_time)
VALUES (1, 2, 'Ordinary Participant', '2024-07-19 10:05:00');

INSERT INTO `Scheduler` (`date`, `member_seq_id`)
VALUES
    ('2024-05-01', 1),
    ('2024-05-02', 2),
    ('2024-05-03', 3);


INSERT INTO `TODO` (`scheduler_seq`, `content`, `completed`)
VALUES
    (1, 'Meeting with client', FALSE),
    (1, 'Prepare presentation', TRUE),
    (2, 'Submit report', TRUE),
    (3, 'Review project plan', FALSE);