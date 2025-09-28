# Guia prático: definir e orquestrar requisições na Page (Atomic Design + Svelte)
Este guia explica, passo a passo e de forma bem detalhada, onde e como concentrar as requisições na Page, mantendo Templates/Organisms/Molecules/Atoms puros e reutilizáveis.  



## 1. Por que a requisição deve ficar na Page?
### Separação de responsabilidades (SRP) é um pilar do Atomic Design:  
**Page:** fonte da verdade para dados e efeitos colaterais (requisições). Decide quando buscar dados, com que parâmetros e o que fazer com os resultados.  
**Template:** organiza layout/slots e distribui props/events. Não busca dados.  
**Organisms/Molecules/Atoms:** componentes presentacionais/controlados. Recebem dados por props e emitem eventos; não sabem de APIs.  

### Benefícios:
**Reuso:** as mesmas moléculas/organismos funcionam em qualquer página.  
**Teste:** fica fácil testar Page (integração) e componentes (unitário).  
**Evolução:** trocar API ou regra de busca não quebra UI.  


## 2. Mapeamento do código de exemplo às camadas
Utilizando um exemplo simples, podemos observar como os componentes se comportam quando aplicamos a separação de responsabilidades. Nesse caso, o valor retornado chega até o template, mas, para fins de simplificação, não será utilizado. A seguir, apresentamos o que cada trecho de código representa.  

+page.svelte            -> Page (requisições,estado,orquestração)  
UsersTemplate.svelte    -> Template (layout, repassa props/events)  
SearchField.svelte      -> Molecule (Input + Button)  
Input.svelte            -> Atom  
Button.svelte           -> Atom  


**Quem sabe o quê?**  
**page:** conhece users, query, loading e a função fetchUsers().  
**UsersTemplate:** só recebe users, query, loading e reemite eventos.  
**SearchField:** recebe modelValue e reemite update:modelValue/search.  
**Input/Button:** somente UI e eventos nativos.  


## 3. Fluxo de ponta a ponta (do clique até os dados)
Podemos observar na imagem que os eventos sempre sobem até o componente Page, enquanto os dados têm sua origem na própria Page e descem para os demais componentes.  

### Regra de ouro:   
Props descem (dados): Page → Template → Molécula → Átomos.  
Eventos sobem (interações): Átomos → Molécula → Template → Page.  
Page é o único lugar que tem estado e faz requisições.  
Templates e Moleculas apenas reemitem eventos e repassam props.  


## 4. Anti-padrões a evitar
Fazer fetch dentro de Template/Organism/Molecule/Atom.  
Passar fetchUsers como prop para ser chamado por baixo (inverte controle).  
Misturar transformações de dados pesadas nos componentes de UI.  
Deixar loading/error escondidos (sem feedback visual).  