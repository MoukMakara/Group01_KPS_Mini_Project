--
-- PostgreSQL database dump
--

-- Dumped from database version 17.2
-- Dumped by pg_dump version 17.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: products; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.products (
    id integer NOT NULL,
    name character varying(50),
    unit_price double precision,
    quantity integer,
    imported_date date DEFAULT CURRENT_DATE
);


ALTER TABLE public.products OWNER TO postgres;

--
-- Name: products_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.products_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.products_id_seq OWNER TO postgres;

--
-- Name: products_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.products_id_seq OWNED BY public.products.id;


--
-- Name: setting; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.setting (
    id integer NOT NULL,
    display_row integer NOT NULL
);


ALTER TABLE public.setting OWNER TO postgres;

--
-- Name: setting_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.setting_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.setting_id_seq OWNER TO postgres;

--
-- Name: setting_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.setting_id_seq OWNED BY public.setting.id;


--
-- Name: products id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products ALTER COLUMN id SET DEFAULT nextval('public.products_id_seq'::regclass);


--
-- Name: setting id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.setting ALTER COLUMN id SET DEFAULT nextval('public.setting_id_seq'::regclass);


--
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.products (id, name, unit_price, quantity, imported_date) FROM stdin;
12	Strawberry	3	50	2025-03-07
13	Mango	2.5	8	2025-03-07
14	Pineapple	4	12	2025-03-07
15	Banana	0.75	25	2025-03-07
16	Orange	1.25	15	2025-03-07
17	Grapes	2	30	2025-03-07
18	Strawberry	3	50	2025-03-07
19	Mango	2.5	8	2025-03-07
20	Pineapple	4	12	2025-03-07
21	Banana	0.75	25	2025-03-07
22	Orange	1.25	15	2025-03-07
23	Grapes	2	30	2025-03-07
24	Strawberry	3	50	2025-03-07
25	Mango	2.5	8	2025-03-07
26	Pineapple	4	12	2025-03-07
27	Banana	0.75	25	2025-03-07
28	Orange	1.25	15	2025-03-07
29	Grapes	2	30	2025-03-07
30	Strawberry	3	50	2025-03-07
31	Mango	2.5	8	2025-03-07
32	Pineapple	4	12	2025-03-07
33	Banana	0.75	25	2025-03-07
34	Orange	1.25	15	2025-03-07
35	Grapes	2	30	2025-03-07
36	Strawberry	3	50	2025-03-07
37	Mango	2.5	8	2025-03-07
38	Pineapple	4	12	2025-03-07
\.


--
-- Data for Name: setting; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.setting (id, display_row) FROM stdin;
1	4
\.


--
-- Name: products_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.products_id_seq', 38, true);


--
-- Name: setting_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.setting_id_seq', 1, true);


--
-- Name: products products_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_pkey PRIMARY KEY (id);


--
-- Name: setting setting_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.setting
    ADD CONSTRAINT setting_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

