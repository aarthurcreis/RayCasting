# Ray Casting Algorithm

Ray casting é uma técnica de renderização que simula a percepção de profundidade em um mundo 2D, projetando raios a partir de um ponto de vista para calcular o que é visível na cena. Foi popularizado por jogos clássicos como **Wolfenstein 3D** (1992) e **DOOM**.

---

## Conceito Fundamental

A ideia central é simples: para cada coluna de pixels na tela, um **raio** é lançado a partir da posição do jogador em uma direção específica. O algoritmo determina onde esse raio colide com uma parede e, com base na distância até essa colisão, calcula a altura da coluna a ser desenhada.

```
Jogador → Raio → Colisão com parede → Altura da coluna na tela
```

Quanto **mais longe** a parede, **menor** a coluna desenhada — criando a ilusão de profundidade.

---

## Estrutura do Mundo

O mundo é representado por uma **grade 2D** (mapa), onde cada célula pode ser uma parede (`1`) ou espaço vazio (`0`):

```
1 1 1 1 1 1
1 0 0 0 0 1
1 0 1 1 0 1
1 0 0 0 0 1
1 1 1 1 1 1
```

O jogador tem:
- **Posição** `(x, y)` — coordenadas no mapa
- **Direção** `(dx, dy)` — vetor unitário para onde está olhando
- **Plano da câmera** — vetor perpendicular à direção, define o campo de visão (FOV)

---

## Passo a Passo do Algoritmo

### 1. Configurar o Raio

Para cada coluna `x` da tela (de `0` a `largura - 1`), calcula-se a direção do raio:

```
cameraX = 2 * x / largura - 1        // valor entre -1 e 1
dirRaioX = dirJogadorX + planoCamX * cameraX
dirRaioY = dirJogadorY + planoCamY * cameraX
```

### 2. DDA — Digital Differential Analysis

O algoritmo **DDA** percorre a grade célula por célula ao longo do raio, de forma eficiente.

**Calcular os incrementos de distância:**

```
deltaDistX = |1 / dirRaioX|   // distância para cruzar 1 unidade em X
deltaDistY = |1 / dirRaioY|   // distância para cruzar 1 unidade em Y
```

**Inicializar passo e distância inicial:**

```
se dirRaioX < 0:
    passoX = -1
    distInicialX = (posX - mapX) * deltaDistX
senão:
    passoX = 1
    distInicialX = (mapX + 1 - posX) * deltaDistX

// mesmo para Y
```

### 3. Loop de Marcha (Ray March)

Avança o raio célula a célula até encontrar uma parede:

```
enquanto não há colisão:
    se distAtualX < distAtualY:
        distAtualX += deltaDistX
        mapX += passoX
        lado = VERTICAL
    senão:
        distAtualY += deltaDistY
        mapY += passoY
        lado = HORIZONTAL

    se mapa[mapX][mapY] > 0:
        colisão = verdadeiro
```

### 4. Calcular a Distância Perpendicular

Para evitar o **efeito fish-eye**, usa-se a **distância perpendicular** ao plano da câmera (não a distância euclidiana direta):

```
se lado == VERTICAL:
    distPerp = (mapX - posX + (1 - passoX) / 2) / dirRaioX
senão:
    distPerp = (mapY - posY + (1 - passoY) / 2) / dirRaioY
```

### 5. Calcular Altura da Coluna

```
alturaColuna = (int)(alturasTela / distPerp)

inicioDesenho = -alturaColuna / 2 + alturaTela / 2
fimDesenho    =  alturaColuna / 2 + alturaTela / 2
```

### 6. Renderizar

Desenha a coluna vertical de pixels com a cor/textura da parede atingida. Paredes atingidas pelo **lado horizontal** podem receber uma tonalidade mais escura para simular iluminação direcional.

---

## Diagrama Visual

```
         Plano da câmera
    ←————————————————————→
          |           |
  Raio    |   Raio    |    Raio
  esq.    |  central  |    dir.
    \     |     |     |     /
     \    |     |     |    /
      \   |     |     |   /
       \  |     |     |  /
        \ |     |     | /
         [P]  Jogador

P = posição do jogador
Os raios se espalham pelo campo de visão (FOV)
```

```
Tela renderizada:

████████████████████████
▓▓▓▓▓[PAREDE PRÓXIMA]▓▓▓   ← coluna alta = parede perto
     ░░░░░░░░░░░░░░░        ← coluna média = distância média
          ···                ← coluna baixa = parede longe
████████████████████████
```

---

## Complexidade

| Aspecto         | Valor                          |
|-----------------|--------------------------------|
| **Tempo**       | O(W × M) — W = largura da tela, M = tamanho máximo do mapa |
| **Espaço**      | O(1) por raio                  |
| **Por frame**   | Um raio por coluna de pixel    |

Na prática, o algoritmo é extremamente rápido, sendo capaz de rodar em tempo real mesmo em hardware modesto.

---

> **Nota:** Ray casting **não é** ray tracing. Ray casting lança raios apenas horizontalmente (em jogos 2D→3D), enquanto ray tracing simula luz de forma completa em 3D, incluindo reflexos e sombras.