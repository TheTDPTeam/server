INSERT INTO public.role(id, role)	VALUES (1, 'ADMIN') ON CONFLICT (id) DO NOTHING;
INSERT INTO public.role(id, role)	VALUES (2, 'STAFF') ON CONFLICT (id) DO NOTHING;
INSERT INTO public.role(id, role)	VALUES (3, 'TEACHER') ON CONFLICT (id) DO NOTHING;
INSERT INTO public.role(id, role)	VALUES (4, 'STUDENT') ON CONFLICT (id) DO NOTHING;
INSERT INTO public.config(id, key, value)	VALUES (1, 'Admin', 'Admin is not configured') ON CONFLICT (id) DO NOTHING;