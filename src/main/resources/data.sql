-- src/main/resources/data.sql


-- SEED DE COMPETÊNCIAS (Pilar essencial do futuro do trabalho)

INSERT INTO competencias (nome, categoria, descricao) VALUES
('Machine Learning (ML)', 'TECNICA', 'Criação e treinamento de modelos de IA e ML.'),
('Computação em Nuvem', 'TECNICA', 'Gerenciamento de ambientes e infraestrutura na nuvem (AWS/Azure/GCP).'),
('Empatia e Escuta Ativa', 'COMPORTAMENTAL', 'Capacidade de entender e responder às necessidades emocionais dos colegas.'),
('Pensamento Crítico', 'COMPORTAMENTAL', 'Análise objetiva de fatos para formar um julgamento.'),
('Gestão de Prioridades', 'SAUDE_MENTAL', 'Habilidade de focar nas tarefas mais importantes e evitar o burnout.'),
('Comunicação Não-Violenta', 'SAUDE_MENTAL', 'Melhora na comunicação interpessoal para reduzir conflitos e estresse.');



-- SEED DE TRILHAS (Diversidade para testar a IA)

INSERT INTO trilhas (nome, descricao, nivel, carga_horaria, foco_principal, ativo, tipo_trilha) VALUES
( 'Reskilling em Análise de Dados', 'Trilha de migração de carreira para Data Analytics.', 'AVANCADO', 160, 'dados', TRUE, 'TECNICA_HARDSKILLS' ),
( 'Fundamentos de IA para Negócios', 'Introdução à IA e seu impacto no mercado de trabalho.', 'INTERMEDIARIO', 80, 'ia', TRUE, 'TECNICA_HARDSKILLS' ),
( 'Primeiros Passos para o Equilíbrio', 'Trilha essencial para quem está em burnout ou estresse crítico.', 'INICIANTE', 30, 'saude mental', TRUE, 'SAUDE_MENTAL_COLABORADOR' ),
( 'Mindfulness e Produtividade', 'Como aplicar a atenção plena no ambiente de trabalho.', 'INTERMEDIARIO', 45, 'saude mental', TRUE, 'SAUDE_MENTAL_COLABORADOR' ),
( 'Comunicação e Feedback Humanizado', 'Aprimoramento das soft skills para gestão de conflitos.', 'INTERMEDIARIO', 60, 'comunicação', TRUE, 'TECNICA_SOFTSKILLS' ),
( 'Liderança Consciente e Ágil', 'Módulos avançados em gestão de equipes remotas.', 'AVANCADO', 100, 'gestão', TRUE, 'GESTAO' ),
( 'Fundamentos de Cloud Computing', 'Introdução à infraestrutura em nuvem e DevOps.', 'INICIANTE', 50, 'nuvem', TRUE, 'TECNICA_HARDSKILLS' ),
( 'Inovação Disruptiva', 'Trilha focada em Design Thinking e novas tecnologias.', 'INTERMEDIARIO', 75, 'inovação', TRUE, 'TECNICA_HARDSKILLS' );



--SEED DE USUÁRIOS (Diversidade de perfis)

INSERT INTO usuarios (nome, email, area_atuacao, nivel_carreira, pilar_principal, data_cadastro) VALUES
('Carolina Souza', 'carol@email.com', 'Analista Financeira', 'PLENO', 'HARDSKILLS', '2024-01-15'),
('Pedro Almeida', 'pedro@email.com', 'Desenvolvedor Frontend', 'JUNIOR', 'SOFTSKILLS', '2024-03-20'),
('Julia Mendes', 'julia@email.com', 'Coordenadora de Atendimento', 'SENIOR', 'GESTAO', '2023-11-01'),
('Rafael Lima', 'rafael@email.com', 'Estudante (Transição)', 'ESTUDANTE', 'HARDSKILLS', '2024-06-10');


--SEED DE ASSOCIAÇÕES TRILHA-COMPETÊNCIA

INSERT INTO trilha_competencia (trilha_id, competencia_id) VALUES
(1, 1), (1, 2),
(2, 1), (2, 2),
(5, 3), (5, 6),
(6, 3), (6, 5),
(3, 5), (4, 6);



-- SEED DE MATRÍCULAS (Exemplo de dados transacionais)

INSERT INTO matriculas (usuario_id, trilha_id, data_inscricao, status) VALUES
(1, 7, '2024-07-01', 'EM_ANDAMENTO'),
(2, 6, '2024-06-10', 'CONCLUIDO'),
(3, 3, '2024-05-20', 'TRANCADO');


-- SEED DE QUESTIONÁRIOS INICIAIS (Crucial para testar a IA)

INSERT INTO questionarios_iniciais (usuario_id, data_resposta, carreira_atual, tempo_carreira_atual_anos, ja_trabalhou_outra_carreira, carreiras_anteriores, gosta_de_fazer, gosta_de_estudar, modo_estudo_preferido, interesse_migracao_tipo, areas_interesse_futuro, disponibilidade_horas_semana, objetivo_carreira, saude_estresse, saude_energia, saude_equilibrio_vida_trabalho, saude_auto_cobranca, indice_saude_mental, nivel_saude_mental, risco_automacao, observacoes_analise) VALUES
(1, CURRENT_DATE(), 'Analista Financeira Senior', 12, TRUE, 'Contabilidade', 'Análise de planilhas e resolver problemas com lógica.', 'Tendências de mercado e tecnologia, excel.', 'LEITURA', 'MUDAR_DE_AREA', 'Data Science, IA, Logística', 8, 'Fazer uma transição organizada para a área de Dados.', 2, 4, 4, 3, 75, 'ESTAVEL', 'ALTO (5-10 anos)', 'Análise inicial concluída.');

INSERT INTO questionarios_iniciais (usuario_id, data_resposta, carreira_atual, tempo_carreira_atual_anos, ja_trabalhou_outra_carreira, carreiras_anteriores, gosta_de_fazer, gosta_de_estudar, modo_estudo_preferido, interesse_migracao_tipo, areas_interesse_futuro, disponibilidade_horas_semana, objetivo_carreira, saude_estresse, saude_energia, saude_equilibrio_vida_trabalho, saude_auto_cobranca, indice_saude_mental, nivel_saude_mental, risco_automacao, observacoes_analise) VALUES
(3, CURRENT_DATE(), 'Coordenadora de Atendimento', 7, FALSE, NULL, 'Gerenciar pessoas e motivar a equipe.', 'Psicologia e comunicação.', 'AULAS_AO_VIVO', 'MIGRAR_PARA_GESTAO', 'Liderança Saudável, RH', 3, 'Manter minha equipe produtiva sem sacrificar o bem-estar.', 5, 1, 1, 5, 10, 'CRITICO', 'MÉDIO (10-15 anos)', 'Análise inicial concluída.');