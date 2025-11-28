<script lang="ts">
	import type { IGetUserDetails } from '$lib/api/queries/users/get-user-details/get-user-details.interface';
	import PlayedTimeContainer from '$lib/components/atoms/PlayedTimeContainer.svelte';
	import GoBack from '$lib/components/molecules/GoBack.svelte';

	export let userDetails: IGetUserDetails;
	export let handleOnEdit: () => void;
	export let handleOnDelete: () => void;
</script>

<main class="flex h-full flex-col gap-10 overflow-y-auto px-1 py-7 sm:px-10 xl:px-15">
	<GoBack
		title={userDetails.name}
		description="Detalhes do usuário"
		onEdit={handleOnEdit}
		onDelete={handleOnDelete}
	/>
	<div
		class="flex flex-wrap items-start justify-between gap-10 px-0 pb-20 sm:px-2 lg:px-4 xl:px-10"
	>
		<section class="flex w-full flex-1 flex-col items-start justify-start gap-4">
			<h3 class="h3 w-full text-start">Dados do Usuário:</h3>
      <div class="flex w-full flex-col items-start justify-start gap-2 py-5">
				<div class="flex w-full gap-2">
					<p class="font-bold">Administrador:</p>
					<p>{userDetails.userType === 'ADMIN' ? 'Sim' : 'Não'}</p>
				</div>
				<div class="flex w-full gap-2">
					<p class="font-bold">Id Público:</p>
					<p>{userDetails.publicId}</p>
				</div>
				<div class="flex w-full gap-2">
					<p class="font-bold">Insituição:</p>
					<p>{userDetails.institution}</p>
				</div>
				<div class="lg:flex w-full lg:gap-2 ">
					<p class="font-bold">E-mail:</p>
					<p>{userDetails.email}</p>
				</div>
				<div class="flex w-full gap-2">
					<p class="font-bold">Telefone:</p>
					<p>{userDetails.phone}</p>
				</div>
				<div class="flex w-full gap-2">
					<p class="font-bold">Classificação Etária:</p>
					<p>{userDetails.ageCategory}</p>
				</div>
				<div class="flex w-full gap-2">
					<p class="font-bold">RA:</p>
					<p>{userDetails.ra}</p>
				</div>
			</div>
		</section>
		<section class="flex flex-1 flex-col items-center justify-center gap-8">
			<h3 class="h3 w-full text-start">Tempo Jogado</h3>
			<div
				class="flex max-h-90 w-full flex-col items-center gap-2 overflow-y-auto px-1 py-2 md:px-4"
			>
				{#if userDetails}
          <PlayedTimeContainer escapeTime={1} boardGamesTime={2} rpgTime={4}/>
				{:else}
					<p class="w-full">Nenhum tempo jogado.</p>
				{/if}
			</div>
		</section>
	</div>
</main>
