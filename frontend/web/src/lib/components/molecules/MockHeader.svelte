<script lang="ts">
  import ProfilePicture from '../atoms/ProfilePicture.svelte';

  const profilePictureSize = '45px';
  const userName = 'FS';
</script>

<header class="header-box flex h-15 w-full items-center justify-between px-7 py-2">
  <div class="flex items-center justify-start gap-4">
    <span class="text-white text-xl font-bold">MockHeader</span>
  </div>

  <div>
    <ProfilePicture
      userName={userName}
      width={profilePictureSize}
      height={profilePictureSize}
      fontSize="18px"
    />
  </div>
</header>

<style>
  .header-box {
    background: var(--primary-color);
    z-index: 11;
    position: relative;
  }
</style>
