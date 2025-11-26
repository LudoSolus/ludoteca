<script lang="ts">
  import MockHeader from "$lib/components/molecules/MockHeader.svelte";
  import EventCard from "$lib/components/molecules/EventCard.svelte";
  import {
    faGamepad,
    faDice,
    faKey,
    faGhost,
    faHeart,
    faPuzzlePiece
  } from "@fortawesome/free-solid-svg-icons";

  const eventos = [
    {
      title: "Evento 55",
      dateStart: new Date("2025-07-20T08:00:00"),
      dateEnd: new Date("2025-07-20T18:00:00"),
      activities: [
        { icon: faGamepad, text: "Jogos de Tabuleiro" },
        { icon: faDice, text: "RPG’s" },
        { icon: faKey, text: "Escape Room" },
      ],
      address: [
        "Rua dos Expedicionários, nº 56",
        "Bairro Alvorada",
        "Cornélio Procópio, PR",
      ],
      image: "/images/event-boardgame.png",
    },
    {
      title: "Noite do Horror",
      dateStart: new Date("2025-07-21T19:00:00"),
      dateEnd: new Date("2025-07-21T23:59:00"),
      activities: [
        { icon: faGhost, text: "RPG de Terror" },
        { icon: faHeart, text: "Card Games" },
        { icon: faPuzzlePiece, text: "Puzzle Zone" },
      ],
      address: [
        "Av. Central, nº 1020",
        "Centro",
        "Cornélio Procópio, PR",
      ],
      image: "/images/event-boardgame.png",
    },
    {
      title: "Festival de Estratégia",
      dateStart: new Date("2025-07-22T14:00:00"),
      dateEnd: new Date("2025-07-22T22:00:00"),
      activities: [
        { icon: faPuzzlePiece, text: "Jogos de Estratégia" },
        { icon: faGamepad, text: "Competição de Tabuleiro" },
        { icon: faHeart, text: "Zona Casual" },
      ],
      address: [
        "Praça Central, nº 200",
        "Jardim Europa",
        "Cornélio Procópio, PR",
      ],
      image: "/images/event-boardgame.png",
    }
  ];
</script>

<MockHeader />

<main class="bg-white min-h-screen w-full px-6 py-10">
  <div class="w-full max-w-[1300px] bg-white">
    <section>
      <h1 class="text-3xl font-bold mb-4 text-left font-inknut">Próximos Eventos</h1>
      <div class="grid grid-cols-1 md:grid-cols-2 gap-10">
        {#each eventos as evento}
          <div class="rounded-xl p-3 bg-white min-h-[380px] flex">
            <EventCard
              title={evento.title}
              dateStart={evento.dateStart}
              dateEnd={evento.dateEnd}
              activities={evento.activities}
              address={evento.address}
              image={evento.image}
            />
          </div>
        {/each}
      </div>
    </section>
  </div>
</main>
