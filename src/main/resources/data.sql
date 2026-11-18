INSERT INTO users (email, password, role, is_active, created_at) VALUES
('student1@test.com', 'password123', 'WORKER', TRUE, CURRENT_TIMESTAMP),
('student2@test.com', 'password123', 'WORKER', TRUE, CURRENT_TIMESTAMP),
('student3@test.com', 'password123', 'WORKER', TRUE, CURRENT_TIMESTAMP),
('admin@test.com', 'password123', 'ADMIN', TRUE, CURRENT_TIMESTAMP);

INSERT INTO workers (user_id, name, phone, skills, available, created_at) VALUES
((SELECT id FROM users WHERE email = 'student1@test.com'), '김철수', '010-1111-2222', 'Java, Spring', TRUE, CURRENT_TIMESTAMP),
((SELECT id FROM users WHERE email = 'student2@test.com'), '이영희', '010-3333-4444', 'React, TypeScript', TRUE, CURRENT_TIMESTAMP),
((SELECT id FROM users WHERE email = 'student3@test.com'), '박민수', NULL, 'Spring, MySQL', FALSE, CURRENT_TIMESTAMP),
((SELECT id FROM users WHERE email = 'admin@test.com'), '관리자', NULL, NULL, FALSE, CURRENT_TIMESTAMP);
