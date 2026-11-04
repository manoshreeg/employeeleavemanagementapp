-- Optional: run this after schema.sql to see the Leave Manager page
-- populated with real rows instead of empty states.
USE employee_management;

INSERT INTO employees (name, department, email) VALUES
  ('Manoshree G', 'Engineering', 'manoshree04@gmail.com');

-- Uses employee id 1 (the row above, on a fresh database)
INSERT INTO leave_requests (employee_id, leave_type, start_date, end_date, reason, status) VALUES
  (1, 'Casual', '2026-08-18', '2026-08-20', 'Family function', 'APPROVED'),
  (1, 'Sick',   '2026-07-02', '2026-07-02', 'Fever',           'APPROVED'),
  (1, 'Casual', '2026-06-15', '2026-06-16', 'Personal work',   'APPROVED'),
  (1, 'Sick',   '2026-05-22', '2026-05-22', 'Not feeling well','REJECTED'),
  (1, 'Earned', '2026-09-28', '2026-10-02', 'Vacation',        'PENDING');
