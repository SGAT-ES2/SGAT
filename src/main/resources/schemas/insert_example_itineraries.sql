TRUNCATE TABLE itinerario_detalhado RESTART IDENTITY CASCADE;

-- Itinerário para Reserva 1
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(1, '2026-08-08', '20:30', 'Voo para a Cidade Principal', 'Voo da Azul Linhas Aéreas, para a cidade principal do destino.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(1, '2026-08-09', '14:45', 'Check-in no Hotel', 'Check-in no hotel e tempo livre para explorar a área.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(1, '2026-08-10', '13:15', 'Dia Livre', 'Dia livre para atividades pessoais e compras.', 'Outro');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(1, '2026-08-11', '18:15', 'Dia Livre', 'Dia livre para atividades pessoais e compras.', 'Outro');

-- Itinerário para Reserva 2
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(2, '2026-10-19', '16:00', 'Voo para a Cidade Principal', 'Voo da Azul Linhas Aéreas, para a cidade principal do destino.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(2, '2026-10-20', '17:45', 'Chegada ao Resort', 'Chegada e acomodação em um resort com tudo incluso.', 'Acomodação');

-- Itinerário para Reserva 3
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(3, '2026-02-16', '14:15', 'Voo Doméstico', 'Voo doméstico para uma cidade vizinha.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(3, '2026-02-17', '10:30', 'Instalação no Lodge', 'Instalação em um lodge ecológico.', 'Acomodação');

-- Itinerário para Reserva 4
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(4, '2026-05-22', '20:30', 'Voo Doméstico', 'Voo doméstico para uma cidade vizinha.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(4, '2026-05-23', '13:30', 'Check-in no Hotel', 'Check-in no hotel e tempo livre para explorar a área.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(4, '2026-05-24', '17:00', 'Voo de Partida', 'Voo da Latam Airlines, saindo de Guarulhos (GRU) para o destino.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(4, '2026-05-25', '08:30', 'Instalação no Lodge', 'Instalação em um lodge ecológico.', 'Acomodação');

-- Itinerário para Reserva 5
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(5, '2026-12-01', '19:00', 'Voo Doméstico', 'Voo doméstico para uma cidade vizinha.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(5, '2026-12-02', '08:15', 'Instalação no Lodge', 'Instalação em um lodge ecológico.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(5, '2026-12-03', '15:00', 'Transfer para o Aeroporto', 'Transfer do hotel para o aeroporto para o voo de volta.', 'Outro');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(5, '2026-12-04', '16:00', 'Tour Histórico', 'Passeio a pé pelo centro histórico e monumentos.', 'Passeio');

-- Itinerário para Reserva 6
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(6, '2026-01-26', '14:45', 'Voo Doméstico', 'Voo doméstico para uma cidade vizinha.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(6, '2026-01-27', '15:00', 'Check-in no Hotel', 'Check-in no hotel e tempo livre para explorar a área.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(6, '2026-01-28', '11:45', 'Check-in no Hotel', 'Check-in no hotel e tempo livre para explorar a área.', 'Acomodação');

-- Itinerário para Reserva 7
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(7, '2026-09-10', '15:15', 'Voo de Partida', 'Voo da Latam Airlines, saindo de Guarulhos (GRU) para o destino.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(7, '2026-09-11', '09:30', 'Chegada ao Resort', 'Chegada e acomodação em um resort com tudo incluso.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(7, '2026-09-12', '13:45', 'Check-in no Hotel', 'Check-in no hotel e tempo livre para explorar a área.', 'Acomodação');

-- Itinerário para Reserva 8
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(8, '2026-04-13', '08:15', 'Voo para a Cidade Principal', 'Voo da Azul Linhas Aéreas, para a cidade principal do destino.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(8, '2026-04-14', '16:00', 'Instalação no Lodge', 'Instalação em um lodge ecológico.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(8, '2026-04-15', '17:45', 'Voo Doméstico', 'Voo doméstico para uma cidade vizinha.', 'Vôo');

-- Itinerário para Reserva 9
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(9, '2026-07-08', '18:45', 'Voo Doméstico', 'Voo doméstico para uma cidade vizinha.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(9, '2026-07-09', '11:00', 'Chegada ao Resort', 'Chegada e acomodação em um resort com tudo incluso.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(9, '2026-07-10', '10:15', 'Instalação no Lodge', 'Instalação em um lodge ecológico.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(9, '2026-07-11', '08:30', 'Voo de Partida', 'Voo da Latam Airlines, saindo de Guarulhos (GRU) para o destino.', 'Vôo');

-- Itinerário para Reserva 10
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(10, '2026-12-25', '12:45', 'Voo para a Cidade Principal', 'Voo da Azul Linhas Aéreas, para a cidade principal do destino.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(10, '2026-12-26', '12:00', 'Instalação no Lodge', 'Instalação em um lodge ecológico.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(10, '2026-12-27', '14:15', 'Dia Livre', 'Dia livre para atividades pessoais e compras.', 'Outro');

-- Itinerário para Reserva 11
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(11, '2026-03-02', '14:15', 'Voo para a Cidade Principal', 'Voo da Azul Linhas Aéreas, para a cidade principal do destino.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(11, '2026-03-03', '17:30', 'Chegada ao Resort', 'Chegada e acomodação em um resort com tudo incluso.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(11, '2026-03-04', '19:00', 'Instalação no Lodge', 'Instalação em um lodge ecológico.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(11, '2026-03-05', '09:30', 'Dia Livre', 'Dia livre para atividades pessoais e compras.', 'Outro');

-- Itinerário para Reserva 12
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(12, '2026-06-19', '17:45', 'Voo de Partida', 'Voo da Latam Airlines, saindo de Guarulhos (GRU) para o destino.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(12, '2026-06-20', '10:15', 'Instalação no Lodge', 'Instalação em um lodge ecológico.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(12, '2026-06-21', '15:00', 'Passeio de Barco', 'Passeio de barco pela costa com paradas para mergulho.', 'Passeio');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(12, '2026-06-22', '11:15', 'Voo para a Cidade Principal', 'Voo da Azul Linhas Aéreas, para a cidade principal do destino.', 'Vôo');

-- Itinerário para Reserva 13
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(13, '2026-09-29', '20:15', 'Voo Doméstico', 'Voo doméstico para uma cidade vizinha.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(13, '2026-09-30', '11:30', 'Chegada ao Resort', 'Chegada e acomodação em um resort com tudo incluso.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(13, '2026-10-01', '10:15', 'Transfer para o Aeroporto', 'Transfer do hotel para o aeroporto para o voo de volta.', 'Outro');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(13, '2026-10-02', '20:15', 'Voo de Partida', 'Voo da Latam Airlines, saindo de Guarulhos (GRU) para o destino.', 'Vôo');

-- Itinerário para Reserva 14
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(14, '2026-02-03', '18:30', 'Voo Doméstico', 'Voo doméstico para uma cidade vizinha.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(14, '2026-02-04', '12:00', 'Chegada ao Resort', 'Chegada e acomodação em um resort com tudo incluso.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(14, '2026-02-05', '18:00', 'Dia Livre', 'Dia livre para atividades pessoais e compras.', 'Outro');

-- Itinerário para Reserva 15
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(15, '2026-10-06', '18:30', 'Voo Doméstico', 'Voo doméstico para uma cidade vizinha.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(15, '2026-10-07', '16:15', 'Check-in no Hotel', 'Check-in no hotel e tempo livre para explorar a área.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(15, '2026-10-08', '18:00', 'Chegada ao Resort', 'Chegada e acomodação em um resort com tudo incluso.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(15, '2026-10-09', '12:45', 'Check-in no Hotel', 'Check-in no hotel e tempo livre para explorar a área.', 'Acomodação');

-- Itinerário para Reserva 16
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(16, '2026-04-21', '11:30', 'Voo de Partida', 'Voo da Latam Airlines, saindo de Guarulhos (GRU) para o destino.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(16, '2026-04-22', '08:15', 'Instalação no Lodge', 'Instalação em um lodge ecológico.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(16, '2026-04-23', '11:45', 'Voo de Partida', 'Voo da Latam Airlines, saindo de Guarulhos (GRU) para o destino.', 'Vôo');

-- Itinerário para Reserva 17
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(17, '2027-01-11', '15:00', 'Voo para a Cidade Principal', 'Voo da Azul Linhas Aéreas, para a cidade principal do destino.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(17, '2027-01-12', '09:30', 'Chegada ao Resort', 'Chegada e acomodação em um resort com tudo incluso.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(17, '2027-01-13', '13:45', 'Passeio de Barco', 'Passeio de barco pela costa com paradas para mergulho.', 'Passeio');

-- Itinerário para Reserva 18
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(18, '2026-08-16', '08:15', 'Voo de Partida', 'Voo da Latam Airlines, saindo de Guarulhos (GRU) para o destino.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(18, '2026-08-17', '19:00', 'Chegada ao Resort', 'Chegada e acomodação em um resort com tudo incluso.', 'Acomodação');

-- Itinerário para Reserva 19
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(19, '2026-11-02', '15:00', 'Voo para a Cidade Principal', 'Voo da Azul Linhas Aéreas, para a cidade principal do destino.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(19, '2026-11-03', '14:45', 'Instalação no Lodge', 'Instalação em um lodge ecológico.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(19, '2026-11-04', '14:00', 'Visita a Museu', 'Visita ao principal museu de arte e história da cidade.', 'Passeio');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(19, '2026-11-05', '16:45', 'Excursão na Natureza', 'Trilha em um parque nacional com guia.', 'Passeio');

-- Itinerário para Reserva 20
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(20, '2026-03-16', '19:45', 'Voo Doméstico', 'Voo doméstico para uma cidade vizinha.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(20, '2026-03-17', '13:45', 'Chegada ao Resort', 'Chegada e acomodação em um resort com tudo incluso.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(20, '2026-03-18', '14:30', 'Check-in no Hotel', 'Check-in no hotel e tempo livre para explorar a área.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(20, '2026-03-19', '11:45', 'Passeio de Barco', 'Passeio de barco pela costa com paradas para mergulho.', 'Passeio');

-- Itinerário para Reserva 21
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(21, '2026-07-23', '09:30', 'Voo de Partida', 'Voo da Latam Airlines, saindo de Guarulhos (GRU) para o destino.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(21, '2026-07-24', '09:45', 'Instalação no Lodge', 'Instalação em um lodge ecológico.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(21, '2026-07-25', '17:15', 'Dia Livre', 'Dia livre para atividades pessoais e compras.', 'Outro');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(21, '2026-07-26', '09:00', 'Voo de Partida', 'Voo da Latam Airlines, saindo de Guarulhos (GRU) para o destino.', 'Vôo');

-- Itinerário para Reserva 22
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(22, '2026-05-02', '17:45', 'Voo Doméstico', 'Voo doméstico para uma cidade vizinha.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(22, '2026-05-03', '17:45', 'Check-in no Hotel', 'Check-in no hotel e tempo livre para explorar a área.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(22, '2026-05-04', '09:30', 'Instalação no Lodge', 'Instalação em um lodge ecológico.', 'Acomodação');

-- Itinerário para Reserva 23
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(23, '2026-09-06', '14:30', 'Voo Doméstico', 'Voo doméstico para uma cidade vizinha.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(23, '2026-09-07', '12:30', 'Instalação no Lodge', 'Instalação em um lodge ecológico.', 'Acomodação');

-- Itinerário para Reserva 24
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(24, '2026-12-02', '17:00', 'Voo Doméstico', 'Voo doméstico para uma cidade vizinha.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(24, '2026-12-03', '19:00', 'Chegada ao Resort', 'Chegada e acomodação em um resort com tudo incluso.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(24, '2026-12-04', '19:00', 'Jantar Típico', 'Jantar em um restaurante com comida e música local.', 'Passeio');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(24, '2026-12-05', '20:45', 'Check-in no Hotel', 'Check-in no hotel e tempo livre para explorar a área.', 'Acomodação');

-- Itinerário para Reserva 25
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(25, '2026-02-19', '17:30', 'Voo Doméstico', 'Voo doméstico para uma cidade vizinha.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(25, '2026-02-20', '18:00', 'Check-in no Hotel', 'Check-in no hotel e tempo livre para explorar a área.', 'Acomodação');

-- Itinerário para Reserva 26
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(26, '2026-08-31', '11:45', 'Voo para a Cidade Principal', 'Voo da Azul Linhas Aéreas, para a cidade principal do destino.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(26, '2026-09-01', '10:15', 'Instalação no Lodge', 'Instalação em um lodge ecológico.', 'Acomodação');

-- Itinerário para Reserva 27
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(27, '2026-10-12', '18:30', 'Voo de Partida', 'Voo da Latam Airlines, saindo de Guarulhos (GRU) para o destino.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(27, '2026-10-13', '17:00', 'Check-in no Hotel', 'Check-in no hotel e tempo livre para explorar a área.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(27, '2026-10-14', '19:15', 'Voo para a Cidade Principal', 'Voo da Azul Linhas Aéreas, para a cidade principal do destino.', 'Vôo');

-- Itinerário para Reserva 28
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(28, '2026-04-06', '11:45', 'Voo Doméstico', 'Voo doméstico para uma cidade vizinha.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(28, '2026-04-07', '12:45', 'Check-in no Hotel', 'Check-in no hotel e tempo livre para explorar a área.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(28, '2026-04-08', '16:15', 'Dia Livre', 'Dia livre para atividades pessoais e compras.', 'Outro');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(28, '2026-04-09', '16:45', 'Voo Doméstico', 'Voo doméstico para uma cidade vizinha.', 'Vôo');

-- Itinerário para Reserva 29
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(29, '2026-11-21', '13:00', 'Voo Doméstico', 'Voo doméstico para uma cidade vizinha.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(29, '2026-11-22', '20:00', 'Check-in no Hotel', 'Check-in no hotel e tempo livre para explorar a área.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(29, '2026-11-23', '17:15', 'Tour Histórico', 'Passeio a pé pelo centro histórico e monumentos.', 'Passeio');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(29, '2026-11-24', '10:00', 'Voo para a Cidade Principal', 'Voo da Azul Linhas Aéreas, para a cidade principal do destino.', 'Vôo');

-- Itinerário para Reserva 30
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(30, '2026-01-31', '17:30', 'Voo para a Cidade Principal', 'Voo da Azul Linhas Aéreas, para a cidade principal do destino.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(30, '2026-02-01', '14:45', 'Chegada ao Resort', 'Chegada e acomodação em um resort com tudo incluso.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(30, '2026-02-02', '09:15', 'Voo de Partida', 'Voo da Latam Airlines, saindo de Guarulhos (GRU) para o destino.', 'Vôo');

-- Itinerário para Reserva 31
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(31, '2026-07-02', '14:00', 'Voo para a Cidade Principal', 'Voo da Azul Linhas Aéreas, para a cidade principal do destino.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(31, '2026-07-03', '11:00', 'Check-in no Hotel', 'Check-in no hotel e tempo livre para explorar a área.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(31, '2026-07-04', '13:15', 'Check-in no Hotel', 'Check-in no hotel e tempo livre para explorar a área.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(31, '2026-07-05', '14:45', 'Dia Livre', 'Dia livre para atividades pessoais e compras.', 'Outro');

-- Itinerário para Reserva 32
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(32, '2026-03-26', '16:00', 'Voo de Partida', 'Voo da Latam Airlines, saindo de Guarulhos (GRU) para o destino.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(32, '2026-03-27', '15:45', 'Check-in no Hotel', 'Check-in no hotel e tempo livre para explorar a área.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(32, '2026-03-28', '20:15', 'Dia Livre', 'Dia livre para atividades pessoais e compras.', 'Outro');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(32, '2026-03-29', '19:00', 'Visita a Museu', 'Visita ao principal museu de arte e história da cidade.', 'Passeio');

-- Itinerário para Reserva 33
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(33, '2026-09-13', '11:45', 'Voo para a Cidade Principal', 'Voo da Azul Linhas Aéreas, para a cidade principal do destino.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(33, '2026-09-14', '13:45', 'Chegada ao Resort', 'Chegada e acomodação em um resort com tudo incluso.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(33, '2026-09-15', '18:00', 'Check-in no Hotel', 'Check-in no hotel e tempo livre para explorar a área.', 'Acomodação');

-- Itinerário para Reserva 34
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(34, '2026-06-06', '12:45', 'Voo de Partida', 'Voo da Latam Airlines, saindo de Guarulhos (GRU) para o destino.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(34, '2026-06-07', '16:00', 'Check-in no Hotel', 'Check-in no hotel e tempo livre para explorar a área.', 'Acomodação');

-- Itinerário para Reserva 35
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(35, '2026-12-16', '13:30', 'Voo Doméstico', 'Voo doméstico para uma cidade vizinha.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(35, '2026-12-17', '09:45', 'Instalação no Lodge', 'Instalação em um lodge ecológico.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(35, '2026-12-18', '14:15', 'Excursão na Natureza', 'Trilha em um parque nacional com guia.', 'Passeio');

-- Itinerário para Reserva 36
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(36, '2026-05-11', '20:15', 'Voo Doméstico', 'Voo doméstico para uma cidade vizinha.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(36, '2026-05-12', '08:45', 'Chegada ao Resort', 'Chegada e acomodação em um resort com tudo incluso.', 'Acomodação');

-- Itinerário para Reserva 37
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(37, '2026-11-12', '12:45', 'Voo Doméstico', 'Voo doméstico para uma cidade vizinha.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(37, '2026-11-13', '08:00', 'Instalação no Lodge', 'Instalação em um lodge ecológico.', 'Acomodação');

-- Itinerário para Reserva 38
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(38, '2026-02-11', '16:30', 'Voo Doméstico', 'Voo doméstico para uma cidade vizinha.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(38, '2026-02-12', '11:15', 'Check-in no Hotel', 'Check-in no hotel e tempo livre para explorar a área.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(38, '2026-02-13', '11:15', 'Check-in no Hotel', 'Check-in no hotel e tempo livre para explorar a área.', 'Acomodação');

-- Itinerário para Reserva 39
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(39, '2026-08-02', '08:00', 'Voo Doméstico', 'Voo doméstico para uma cidade vizinha.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(39, '2026-08-03', '19:00', 'Instalação no Lodge', 'Instalação em um lodge ecológico.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(39, '2026-08-04', '11:45', 'Chegada ao Resort', 'Chegada e acomodação em um resort com tudo incluso.', 'Acomodação');

-- Itinerário para Reserva 40
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(40, '2026-10-21', '12:15', 'Voo para a Cidade Principal', 'Voo da Azul Linhas Aéreas, para a cidade principal do destino.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(40, '2026-10-22', '17:15', 'Check-in no Hotel', 'Check-in no hotel e tempo livre para explorar a área.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(40, '2026-10-23', '16:45', 'Voo de Partida', 'Voo da Latam Airlines, saindo de Guarulhos (GRU) para o destino.', 'Vôo');

-- Itinerário para Reserva 41
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(41, '2026-04-16', '18:00', 'Voo Doméstico', 'Voo doméstico para uma cidade vizinha.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(41, '2026-04-17', '11:30', 'Check-in no Hotel', 'Check-in no hotel e tempo livre para explorar a área.', 'Acomodação');

-- Itinerário para Reserva 42
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(42, '2026-09-19', '11:45', 'Voo Doméstico', 'Voo doméstico para uma cidade vizinha.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(42, '2026-09-20', '15:45', 'Check-in no Hotel', 'Check-in no hotel e tempo livre para explorar a área.', 'Acomodação');

-- Itinerário para Reserva 43
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(43, '2026-01-16', '08:45', 'Voo de Partida', 'Voo da Latam Airlines, saindo de Guarulhos (GRU) para o destino.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(43, '2026-01-17', '08:45', 'Instalação no Lodge', 'Instalação em um lodge ecológico.', 'Acomodação');

-- Itinerário para Reserva 44
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(44, '2026-07-13', '13:30', 'Voo Doméstico', 'Voo doméstico para uma cidade vizinha.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(44, '2026-07-14', '19:00', 'Check-in no Hotel', 'Check-in no hotel e tempo livre para explorar a área.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(44, '2026-07-15', '18:00', 'Voo para a Cidade Principal', 'Voo da Azul Linhas Aéreas, para a cidade principal do destino.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(44, '2026-07-16', '15:30', 'Voo Doméstico', 'Voo doméstico para uma cidade vizinha.', 'Vôo');

-- Itinerário para Reserva 45
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(45, '2026-03-09', '18:15', 'Voo Doméstico', 'Voo doméstico para uma cidade vizinha.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(45, '2026-03-10', '19:15', 'Instalação no Lodge', 'Instalação em um lodge ecológico.', 'Acomodação');

-- Itinerário para Reserva 46
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(46, '2026-12-29', '18:45', 'Voo de Partida', 'Voo da Latam Airlines, saindo de Guarulhos (GRU) para o destino.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(46, '2026-12-30', '13:45', 'Chegada ao Resort', 'Chegada e acomodação em um resort com tudo incluso.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(46, '2026-12-31', '12:00', 'Transfer para o Aeroporto', 'Transfer do hotel para o aeroporto para o voo de volta.', 'Outro');

-- Itinerário para Reserva 47
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(47, '2026-05-26', '20:45', 'Voo para a Cidade Principal', 'Voo da Azul Linhas Aéreas, para a cidade principal do destino.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(47, '2026-05-27', '10:15', 'Check-in no Hotel', 'Check-in no hotel e tempo livre para explorar a área.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(47, '2026-05-28', '09:15', 'Excursão na Natureza', 'Trilha em um parque nacional com guia.', 'Passeio');

-- Itinerário para Reserva 48
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(48, '2026-11-06', '08:15', 'Voo de Partida', 'Voo da Latam Airlines, saindo de Guarulhos (GRU) para o destino.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(48, '2026-11-07', '13:45', 'Instalação no Lodge', 'Instalação em um lodge ecológico.', 'Acomodação');

-- Itinerário para Reserva 49
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(49, '2026-02-26', '18:30', 'Voo Doméstico', 'Voo doméstico para uma cidade vizinha.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(49, '2026-02-27', '10:00', 'Check-in no Hotel', 'Check-in no hotel e tempo livre para explorar a área.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(49, '2026-02-28', '09:30', 'Voo Doméstico', 'Voo doméstico para uma cidade vizinha.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(49, '2026-03-01', '10:30', 'Dia Livre', 'Dia livre para atividades pessoais e compras.', 'Outro');

-- Itinerário para Reserva 50
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(50, '2026-08-21', '16:45', 'Voo de Partida', 'Voo da Latam Airlines, saindo de Guarulhos (GRU) para o destino.', 'Vôo');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(50, '2026-08-22', '17:30', 'Chegada ao Resort', 'Chegada e acomodação em um resort com tudo incluso.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(50, '2026-08-23', '12:30', 'Chegada ao Resort', 'Chegada e acomodação em um resort com tudo incluso.', 'Acomodação');
INSERT INTO itinerario_detalhado (reserva_id, data, horario, titulo, descricao, tipo) VALUES
(50, '2026-08-24', '20:15', 'Instalação no Lodge', 'Instalação em um lodge ecológico.', 'Acomodação');

