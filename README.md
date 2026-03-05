# Refatoração do Build Pipeline

Este projeto consiste em refatorar o código que está no repositório  
**BuildPipeline-Refactoring-Kata** da Emily Bache.

O objetivo foi melhorar a qualidade do código mantendo o mesmo comportamento da aplicação.

---

# 1. Projeto Original

O projeto original é um pipeline que executa as seguintes etapas:

1. Faz os testes do projeto
2. Se os testes passarem, faz o deploy
3. Envia um email resumindo o que aconteceu na execução

---

# 2. Problemas Identificados

Identifiquei alguns problemas de qualidade que dificultavam a manutenção e entendimento do sistema:

- O método **run()** era muito grande e com várias responsabilidades diferentes
- IFs aninhados que dificultam a leitura do código
- Uso de **strings mágicas** como "success"

Esses problemas tornam o código mais difícil de manter e aumentam o risco de erros em caso de alterações futuras.

---

# 3. Melhorias Realizadas

As seguintes melhorias foram aplicadas durante o processo de refatoração:

### Criação de testes automatizados

Antes de começar a refatoração, criei testes unitários para garantir que o 
comportamento original do sistema fosse mantido.

### Extração de métodos

O método run() foi dividido em métodos menores:

- execução dos testes
- execução do deploy
- envio de email

Isso melhorou a legibilidade e a organização do código.

### Redução de condicionais aninhadas

Foram utilizadas verificações antecipadas para reduzir a quantidade de IFs aninhados, 
tornando o fluxo de execução mais claro.

### Uso de contante

Utilizei constantes facilitando futuras alterações do código.

---

# 4. Justificativas

### Single Responsibility Principle(SRP)

Separação da lógica em métodos menores, permitindo que cada método tenha apenas
uma responsabilidade.

### Melhor legibilidade

Métodos menores e menos condicionais aninhadas tornando o código
mais fácil de entender e manter.

### Segurança em futuras alterações

A criação de testes automatizados antes da refatoração garantiu que
o comportamento original do sistema fosse preservado.