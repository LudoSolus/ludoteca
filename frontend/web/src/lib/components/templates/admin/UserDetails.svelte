<script lang="ts">
	import type { IGetUserDetails } from '$lib/api/queries/users/get-user-details/get-user-details.interface';
	import type { IChartItem } from '$lib/components/atoms/PieChart.svelte';
	import PieChart from '$lib/components/atoms/PieChart.svelte';
	import Tooltip from '$lib/components/atoms/Tooltip.svelte';
	import GoBack from '$lib/components/molecules/GoBack.svelte';
	import { ETooltipBehavior } from '$lib/shared/enums/tooltip-behavior.enum';

	export let userDetails: IGetUserDetails;
	export let handleOnEdit: () => void;
	export let handleOnDelete: () => void;

	$: statsData = [
		{ label: 'Board Games', value: userDetails.hoursEscapRoom, 	color: '#7C8DFF' },
		{ label: 'RPG',         value: userDetails.hoursBoardGames, color: '#00E699' },
		{ label: 'Escape Room', value: userDetails.hoursRpg, 				color: '#A855F7' }
	] as IChartItem[];

  let clientEmailWidth: number = 0;
	let emailRef: HTMLParagraphElement;
  let isEmailTruncate: boolean = false;
	$: {
    if (emailRef && userDetails.email) {
      isEmailTruncate = emailRef.scrollWidth > clientEmailWidth;
    }
  }

</script>

<main class="flex h-full flex-col gap-10 px-5 py-7 sm:px-12 xl:px-15">
	<GoBack
		title={userDetails.name}
		description="Detalhes do usuário"
		onEdit={handleOnEdit}
		onDelete={handleOnDelete}
	/>
	<div
		class="flex md:flex-row flex-col items-start justify-between gap-10 pb-20 px-2 lg:px-4 md:px-10"
	>
		<section class="flex w-full flex-1 flex-col items-start justify-start gap-4">
			<h3 class="h3 w-full text-start">Dados do Usuário:</h3>
      <div class="flex w-full flex-col items-start justify-start gap-2 py-5">
				<div class="flex w-full gap-2 flex-wrap">
					<p class="font-bold">Administrador:</p>
					<p>{userDetails.userType === 'ADMIN' ? 'Sim' : 'Não'}</p>
				</div>
				<div class="flex w-full gap-2 flex-wrap">
					<p class="font-bold">Id Público:</p>
					<p>{userDetails.publicId}</p>
				</div>
				<div class="flex w-full gap-2 flex-wrap">
					<p class="font-bold">Insituição:</p>
					<p>{userDetails.institution}</p>
				</div>
				<div class="lg:flex w-full lg:gap-2 ">
					<p class="font-bold">E-mail:</p>
					{#if isEmailTruncate}
						<Tooltip
							text={userDetails.email}
							classes="relative inline-block w-full"
							type={ETooltipBehavior.Click | ETooltipBehavior.FocusIn | ETooltipBehavior.FocusOut}
						>
							<p bind:clientWidth={clientEmailWidth} bind:this={emailRef} class="truncate text-start text-sm cursor-pointer">
								{userDetails.email}
							</p>
						</Tooltip>
					{:else}
						<p bind:clientWidth={clientEmailWidth} bind:this={emailRef} class="truncate text-start text-sm">
							{userDetails.email}
						</p>
					{/if}
				</div>
				<div class="flex w-full gap-2 flex-wrap">
					<p class="font-bold">Telefone:</p>
					<p>{userDetails.phone}</p>
				</div>
				<div class="flex w-full gap-2 flex-wrap">
					<p class="font-bold">Classificação Etária:</p>
					<p>{userDetails.ageCategory === "+18" ? "Maioridade" : userDetails.ageCategory}</p>
				</div>
				<div class="flex w-full gap-2 flex-wrap">
					<p class="font-bold">RA:</p>
					<p>{userDetails.ra}</p>
				</div>
			</div>
		</section>
		<section class="flex flex-1 h-full w-full flex-col items-center justify-center gap-8">
			<h3 class="h3 w-full text-start">Tempo Jogado:</h3>
			<div
				class="flex h-full max-h-90 w-full flex-col flex-1 items-center gap-2 overflow-y-auto px-1 py-2 md:px-4"
			>
				{#if userDetails.hoursEscapRoom || userDetails.hoursBoardGames || userDetails.hoursRpg}
          <PieChart items={statsData} unit={'horas'}/>
				{:else}
					<p class="w-full">Tempo de jogo insuficiente ou inexistente para o usuário.</p>
				{/if}
			</div>
		</section>
	</div>
</main>
