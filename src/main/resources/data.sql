insert into Incident_Types(Incident_Type_Id, Type, Expected_SLA_In_Days) values
(20001, 1, 3),
(20002, 2, 7),
(20003, 3, 10),
(20004, 4, 2);

--insert into incidents(incident_id, incident_date, report_date, incident_reported_by_user_id, resolution_eta, incident_details, booking_id, status, incident_type_id) values
--('2024-1128','2024-04-18',	'2024-04-20',748116,'2024-04-23','write in details',19379,'PENDING', 20001);

insert into Users(user_name, password, role) values
('user123', '12345', 'user'),
('admin123', '12345', 'admin');