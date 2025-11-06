<script lang="ts">
  import EventCard from "$lib/components/molecules/EventCard.svelte";
  import {
    faGamepad,
    faDice,
    faKey,
    faGhost,
    faPuzzlePiece,
    faHeart
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
  ];
</script>

<main class="min-h-screen bg-[#fffcee] p-10 space-y-12">
  <section class="flex flex-col items-center gap-8">
    {#each eventos as evento}
      <EventCard
        title={evento.title}
        dateStart={evento.dateStart}
        dateEnd={evento.dateEnd}
        activities={evento.activities}
        address={evento.address}
        image={evento.image}
      />
    {/each}
  </section>
</main>
