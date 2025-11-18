<script lang="ts">
  import ProfilePicture from "$lib/components/atoms/ProfilePicture.svelte";
  export let name: string;
</script>

<div
  class="flex items-center gap-[10px] bg-white rounded-[10px] 
         border border-[#000000] px-[20px] py-[10px] 
         w-[239px] h-[60px]"
  style="box-shadow: 0px 4px 4px rgba(0,0,0,0.25);"
>
  <ProfilePicture userName={name} />

  <span
    class="text-gray-900"
    style="
      font-family: 'Inknut Antiqua', serif;
      font-weight: 500;
      font-size: 18px;
      line-height: 22px;
      letter-spacing: 0;
    "
  >
    {name}
  </span>
</div>
