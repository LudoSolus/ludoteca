<script lang="ts">
	import { goto } from "$app/navigation";
	import type { IUser } from "$lib/api/queries/users/list-users/list-users.interface";
	import Button from "$lib/components/atoms/Button.svelte";
	import SearchInput from "$lib/components/atoms/SearchInput.svelte";
	import UserContainer from "$lib/components/atoms/UserContainer.svelte";
	import { faPlus } from "@fortawesome/free-solid-svg-icons";

  export let users: IUser[];
  let searchInputLeftValue: string = '';
  let searchInputRightValue: string = '';

  const goToUserDetails = (userId: string) => {
    goto(`/admin/users/${userId}`);
  }

  $: filteredUsersList = users.filter((user) => {
    return user.email.toLowerCase().includes(searchInputLeftValue.toLocaleLowerCase()) && user.roleUser !== 'ADMIN';
  })
  $: filteredAdminsList = users.filter((user) => {
    return user.email.toLowerCase().includes(searchInputRightValue.toLocaleLowerCase())  && user.roleUser === 'ADMIN';
  })
</script>

<main class="flex w-full flex-col items-center justify-center gap-10 overflow-y-auto px-4 py-2 sm:px-10">
  <div class="self-end pt-10">
    <Button text={'Criar'} leftIcon={faPlus} onClick={() => {alert('Página de criar um Usuário!')}} />
  </div>
  <div class="flex h-full items-center lg:items-start justify-center lg:justify-between flex-col lg:flex-row lg:w-full">
    
    <!-- USERS -->
     <section class="flex w-full max-w-150 flex-1 flex-col items-center justify-center gap-8 pt-0 lg:px-10">
      <div class="flex w-full max-w-128 flex-col gap-4">
        
        <h3 class="h3 gap-5">Usuários</h3>
        
        <div class="flex w-full max-w-128 items-center justify-start gap-5 px-1">
          <SearchInput height={'100%'} bind:value={searchInputLeftValue} placeholder="Pesquisar..." />
        </div>
        
        <div
          class="flex max-h-72 min-h-72 w-full flex-col flex-1 items-center gap-2 overflow-y-auto px-1 py-2 scrollbar-clean"
        >
          {#if filteredAdminsList.length > 0}
            {#each filteredUsersList as user}
              <UserContainer
                email={user.email}
                ageGroup={user.ageCategory === '+18' ? 'Maioridade' : user.ageCategory}
                onClick={() => {
                  goToUserDetails(user.id);
                }}
              />
            {/each}
          {:else}
            <p class="w-full">Nenhum usuário encontrado.</p>
          {/if}
        </div>
      </div>
    </section>

    <!-- ADMINS -->
    <section class="flex max-w-150 flex-1 flex-col items-center justify-center gap-8 pt-15 lg:pt-0 lg:px-10">
      <div class="flex w-full max-w-128 flex-col gap-4">
        
        <h3 class="h3 gap-5">Administradores</h3>
        
        <div class="flex w-full max-w-128 items-center justify-start gap-5 px-1">
          <SearchInput height={'100%'} bind:value={searchInputRightValue} placeholder="Pesquisar..." />
        </div>
        
        <div
          class="flex max-h-72 min-h-72 w-full flex-col flex-1 items-center gap-2 overflow-y-auto px-1 py-2 scrollbar-clean"
        >
          {#if filteredAdminsList.length > 0}
            {#each filteredAdminsList as admin}
              <UserContainer
                email={admin.email}
                ageGroup={admin.ageCategory === '+18' ? 'Maioridade' : admin.ageCategory}
                isRpgMaster={true}
                onClick={() => {
                  goToUserDetails(admin.id);
                }}
              />
            {/each}
          {:else}
            <p class="w-full">Nenhum administrador encontrado.</p>
          {/if}
        </div>
      </div>
    </section>
  </div>

</main>