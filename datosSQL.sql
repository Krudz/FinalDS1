
CREATE TABLE public.cliente (
    docidcl character varying(50) NOT NULL,
    nombrecl character varying(50) NOT NULL,
    apellidocl character varying(50) NOT NULL,
    fechanaccl date,
    generocl character varying(50) NOT NULL,
    direccioncl character varying(50) NOT NULL,
    telefonocl character varying(50) NOT NULL,
    emailcl character varying(50) NOT NULL,
    estado character varying(20) NOT NULL
);


ALTER TABLE public.cliente OWNER TO postgres;


CREATE TABLE public.factura (
    "nroRefFactura" character varying(50) NOT NULL,
    "docIdCl" character varying(50),
    "fechaFactura" date,
    "medioPago" character varying(50),
    "totalFactura" double precision,
    utilidad double precision,
    vendedor character varying(50),
    hora character varying(50)
);


ALTER TABLE public.factura OWNER TO postgres;


CREATE TABLE public.itemfactura (
    "nroRefFactura" character varying(50),
    "codRefProd" character varying(50) NOT NULL,
    descripcion character varying(50),
    "valorUnitario" character varying(50),
    cantidad integer,
    subtotal character varying(50)
);


ALTER TABLE public.itemfactura OWNER TO postgres;



CREATE TABLE public.producto (
    codrefprod character varying(50) NOT NULL,
    codigoprov character varying(50),
    tipoformato character varying(50) NOT NULL,
    nomartista character varying(50) NOT NULL,
    album character varying(50) NOT NULL,
    generoal character varying(50) NOT NULL,
    costoprod character varying(50) NOT NULL,
    precioventaprod character varying(50) NOT NULL,
    cantidadstock character varying(50),
    estado character varying(20) NOT NULL
);


ALTER TABLE public.producto OWNER TO postgres;



CREATE TABLE public.proveedor (
    codigoprov character varying(50) NOT NULL,
    nitprov character varying(50) NOT NULL,
    nombreprov character varying(50) NOT NULL,
    direccionprov character varying(50) NOT NULL,
    telefonoprov character varying(50) NOT NULL,
    emailprov character varying(50) NOT NULL,
    estado character varying(20) NOT NULL
);


ALTER TABLE public.proveedor OWNER TO postgres;



CREATE TABLE public.usuario (
    docidus character varying(50) NOT NULL,
    nombreus character varying(50) NOT NULL,
    apellidous character varying(50) NOT NULL,
    fechanacus date,
    telefonous character varying(50) NOT NULL,
    generous character varying(50) NOT NULL,
    direccionus character varying(50) NOT NULL,
    cargous character varying(50) NOT NULL,
    passwordus character varying(50) NOT NULL,
    estado character varying(20) NOT NULL
);


ALTER TABLE public.usuario OWNER TO postgres;




ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_pkey PRIMARY KEY (docidcl);



ALTER TABLE ONLY public.factura
    ADD CONSTRAINT "nroRefFactura" PRIMARY KEY ("nroRefFactura");




ALTER TABLE ONLY public.producto
    ADD CONSTRAINT producto_pkey PRIMARY KEY (codrefprod);




ALTER TABLE ONLY public.proveedor
    ADD CONSTRAINT proveedor_pkey PRIMARY KEY (codigoprov);




ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (docidus);



ALTER TABLE ONLY public.factura
    ADD CONSTRAINT "docIdCl" FOREIGN KEY ("docIdCl") REFERENCES public.cliente(docidcl);



ALTER TABLE ONLY public.itemfactura
    ADD CONSTRAINT "nroRefFactura" FOREIGN KEY ("nroRefFactura") REFERENCES public.factura("nroRefFactura");




ALTER TABLE ONLY public.producto
    ADD CONSTRAINT producto_codigoprov_fkey FOREIGN KEY (codigoprov) REFERENCES public.proveedor(codigoprov) ON DELETE SET DEFAULT;




INSERT INTO public.cliente (docidcl, nombrecl, apellidocl, fechanaccl, generocl, direccioncl, telefonocl, emailcl, estado) VALUES ('3423432', 'Andres', 'Vargas', '1992-09-12', 'M', 'Calle 5 con 26', '3122049734', 'andr234@hotmail.com', 'Activo');
INSERT INTO public.cliente (docidcl, nombrecl, apellidocl, fechanaccl, generocl, direccioncl, telefonocl, emailcl, estado) VALUES ('4213233', 'Julian', 'Ballesteros', '1990-09-04', 'M', 'Calle 45 #45-33', '242354', 'jballe@hotmail.com', 'Activo');
INSERT INTO public.cliente (docidcl, nombrecl, apellidocl, fechanaccl, generocl, direccioncl, telefonocl, emailcl, estado) VALUES ('1104534523', 'Carmen', 'Villalobos', '1985-09-03', 'F', 'Altos de Chipichape', '6547594', 'carmlobo@gmail.com', 'Activo');
INSERT INTO public.cliente (docidcl, nombrecl, apellidocl, fechanaccl, generocl, direccioncl, telefonocl, emailcl, estado) VALUES ('31471652', 'Andrea ', 'Galindo', '1965-06-14', 'F', 'Calle 3 Oeste #25-63', '8749635', 'andreagad@yahoo.es', 'Activo');
INSERT INTO public.cliente (docidcl, nombrecl, apellidocl, fechanaccl, generocl, direccioncl, telefonocl, emailcl, estado) VALUES ('54987254', 'Richard', 'Martinez', '1982-11-12', 'M', 'Calle 43 # 8-74', '6541213', 'richadar@hotmail.com', 'Activo');
INSERT INTO public.cliente (docidcl, nombrecl, apellidocl, fechanaccl, generocl, direccioncl, telefonocl, emailcl, estado) VALUES ('647895230', 'Carolina', 'Gomez', '1996-05-14', 'F', 'Calle 35 #5-41', '3218975641', 'carol5812@gmail.com', 'Activo');
INSERT INTO public.cliente (docidcl, nombrecl, apellidocl, fechanaccl, generocl, direccioncl, telefonocl, emailcl, estado) VALUES ('315874521', 'Alvaro', 'Sierra', '2009-09-01', 'M', 'Calle 43 #2-35', '4541544', 'sier@gmail.com', 'Inactivo');
INSERT INTO public.cliente (docidcl, nombrecl, apellidocl, fechanaccl, generocl, direccioncl, telefonocl, emailcl, estado) VALUES ('3878234', 'Gabriel', 'Marquez', '2019-09-02', 'M', 'Calle 8 #43-20', '6748932', 'gabo@yahoo.es', 'Inactivo');
INSERT INTO public.cliente (docidcl, nombrecl, apellidocl, fechanaccl, generocl, direccioncl, telefonocl, emailcl, estado) VALUES ('31874953', 'Juan', 'Colorado', '2019-09-02', 'M', 'Calle 9 #45-34', '3254586', 'juanc@gmail.com', 'Inactivo');


--
-- TOC entry 2842 (class 0 OID 32822)
-- Dependencies: 197
-- Data for Name: factura; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.factura ("nroRefFactura", "docIdCl", "fechaFactura", "medioPago", "totalFactura", utilidad, vendedor, hora) VALUES ('001', '1104534523', '2019-09-10', 'Tarjeta', 121000, 26000, 'Christian Rodriguez', '11:45');
INSERT INTO public.factura ("nroRefFactura", "docIdCl", "fechaFactura", "medioPago", "totalFactura", utilidad, vendedor, hora) VALUES ('002', '315874521', '2019-09-10', 'Efectivo', 54000, 14000, 'Christian Rodriguez', '11:56');


--
-- TOC entry 2843 (class 0 OID 32825)
-- Dependencies: 198
-- Data for Name: itemfactura; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.itemfactura ("nroRefFactura", "codRefProd", descripcion, "valorUnitario", cantidad, subtotal) VALUES ('001', '003', 'CD - Richie Ray - Conga de Libertad - Salsa', '67000', 1, '67000');
INSERT INTO public.itemfactura ("nroRefFactura", "codRefProd", descripcion, "valorUnitario", cantidad, subtotal) VALUES ('001', '004', 'CD - Sebastian Yepes - Impregnado - Pop', '54000', 1, '54000');
INSERT INTO public.itemfactura ("nroRefFactura", "codRefProd", descripcion, "valorUnitario", cantidad, subtotal) VALUES ('002', '004', 'CD - Sebastian Yepes - Impregnado - Pop', '54000', 1, '54000');


--
-- TOC entry 2845 (class 0 OID 32831)
-- Dependencies: 200
-- Data for Name: proveedor; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.proveedor (codigoprov, nitprov, nombreprov, direccionprov, telefonoprov, emailprov, estado) VALUES ('1091', '34242351-3', 'Truina Records', 'Calle 9 #45-21', '3445687', 'truina@gmail.com', 'Activo');
INSERT INTO public.proveedor (codigoprov, nitprov, nombreprov, direccionprov, telefonoprov, emailprov, estado) VALUES ('1092', '34273872-1', 'Kajastan Sup', 'Calle 5 # 25-41', '3527489', 'ksup@gmail.com', 'Activo');
INSERT INTO public.proveedor (codigoprov, nitprov, nombreprov, direccionprov, telefonoprov, emailprov, estado) VALUES ('1094', '66484514-7', 'Turkish Studios', 'Calle 34 # 7-53', '5674213', 'turkisstudios@hotmail.com', 'Activo');
INSERT INTO public.proveedor (codigoprov, nitprov, nombreprov, direccionprov, telefonoprov, emailprov, estado) VALUES ('1093', '131424556-1', 'Amda RCD', 'Calle 100 # 5-67', '4716547', 'amda@hotmail.com', 'Inactivo');


--
-- TOC entry 2844 (class 0 OID 32828)
-- Dependencies: 199
-- Data for Name: producto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.producto (codrefprod, codigoprov, tipoformato, nomartista, album, generoal, costoprod, precioventaprod, cantidadstock, estado) VALUES ('001', '1092', 'CD', 'Juanes', 'Calma', 'Pop', '34000', '45000', '8', 'Inactivo');
INSERT INTO public.producto (codrefprod, codigoprov, tipoformato, nomartista, album, generoal, costoprod, precioventaprod, cantidadstock, estado) VALUES ('002', '1091', 'CD', 'Tito Rojas', 'Siempre Sere', 'Salsa', '60000', '75000', '18', 'Activo');
INSERT INTO public.producto (codrefprod, codigoprov, tipoformato, nomartista, album, generoal, costoprod, precioventaprod, cantidadstock, estado) VALUES ('004', '1091', 'CD', 'Sebastian Yepes', 'Impregnado', 'Pop', '40000', '54000', '21', 'Activo');
INSERT INTO public.producto (codrefprod, codigoprov, tipoformato, nomartista, album, generoal, costoprod, precioventaprod, cantidadstock, estado) VALUES ('003', '1091', 'CD', 'Richie Ray', 'Conga de Libertad', 'Salsa', '55000', '67000', '12', 'Activo');


--
-- TOC entry 2846 (class 0 OID 32834)
-- Dependencies: 201
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.usuario (docidus, nombreus, apellidous, fechanacus, telefonous, generous, direccionus, cargous, passwordus, estado) VALUES ('16728135', 'Victor', 'Rodriguez', '1974-12-06', '3125846312', 'M', 'Calle 33 16-98', 'Estandar', '748576501', 'Activo');
INSERT INTO public.usuario (docidus, nombreus, apellidous, fechanacus, telefonous, generous, direccionus, cargous, passwordus, estado) VALUES ('1107066494', 'Christian', 'Rodriguez', '1992-01-05', '3658741', 'M', 'Calle 4 #8-41', 'Administrador', '748576273', 'Activo');
INSERT INTO public.usuario (docidus, nombreus, apellidous, fechanacus, telefonous, generous, direccionus, cargous, passwordus, estado) VALUES ('31973364', 'Ana', 'Ruales', '1969-08-13', '3003443901', 'F', 'Calle 9 # 14-7', 'Estandar', '748580435', 'Activo');












