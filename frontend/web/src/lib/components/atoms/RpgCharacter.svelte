<script lang="ts">
  import ProfilePicture from "$lib/components/atoms/ProfilePicture.svelte";

  export let name: string = "Elza Rainfrost";
</script>

<div class="flex flex-col items-start gap-1">
  <div
    class="flex items-center gap-[10px] bg-white rounded-[10px] 
           border border-[#000000] px-[20px] py-[10px] 
           w-[239px] h-[60px]"
    style="box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);"
  >
    <!-- Avatar com iniciais dinâmicas -->
    <ProfilePicture userName={name} />

    <!-- Nome do personagem -->
    <span class="font-bold text-gray-900 text-[20px] leading-none">{name}</span>
  </div>
</div>
