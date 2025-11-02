<script lang="ts">
  import RpgCharacter from "$lib/components/atoms/RpgCharacter.svelte";

  const personagens = [
    { name: "Elza Rainfrost" },
    { name: "Arthos Varyn" }
  ];
</script>

<main class="min-h-screen bg-[#fffcee] p-10 space-y-12">
  <section class="flex flex-col items-start gap-4">
    {#each personagens as personagem}
      <RpgCharacter name={personagem.name} />
    {/each}
  </section>
</main>
