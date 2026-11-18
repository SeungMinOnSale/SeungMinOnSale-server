INSERT INTO users (email, password, role, is_active, created_at, updated_at)
VALUES ('student1@test.com', 'password123', 'WORKER', TRUE, '2026-11-10 09:00:00', '2026-11-10 09:00:00'),
       ('student2@test.com', 'password123', 'WORKER', TRUE, '2026-11-10 09:10:00', '2026-11-10 09:10:00'),
       ('student3@test.com', 'password123', 'WORKER', TRUE, '2026-11-11 13:20:00', '2026-11-11 13:20:00'),
       ('admin@test.com', 'password123', 'ADMIN', TRUE, '2026-11-09 08:00:00', '2026-11-09 08:00:00');

INSERT INTO workers (user_id, name, phone, skills, available, created_at, updated_at)
VALUES ((SELECT id FROM users WHERE email = 'student1@test.com'), '김철수', '010-1111-2222', 'Java, Spring', TRUE,
        '2026-11-10 09:00:00', '2026-11-10 09:00:00'),
       ((SELECT id FROM users WHERE email = 'student2@test.com'), '이영희', '010-3333-4444', 'React, TypeScript', TRUE,
        '2026-11-10 09:10:00', '2026-11-10 09:10:00'),
       ((SELECT id FROM users WHERE email = 'student3@test.com'), '박민수', NULL, 'Spring, MySQL', FALSE,
        '2026-11-11 13:20:00', '2026-11-11 13:20:00'),
       ((SELECT id FROM users WHERE email = 'admin@test.com'), '관리자', NULL, NULL, FALSE,
        '2026-11-09 08:00:00', '2026-11-09 08:00:00');
