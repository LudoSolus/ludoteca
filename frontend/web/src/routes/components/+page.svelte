<script lang="ts">
	import BoardGame from '$lib/components/atoms/BoardGame.svelte';
	import Button from '$lib/components/atoms/Button.svelte';
	import Input from '$lib/components/atoms/Input.svelte';
	import ProfilePicture from '$lib/components/atoms/ProfilePicture.svelte';
	import SelectInput from '$lib/components/atoms/SelectInput.svelte';
	import UserContainer from '$lib/components/atoms/UserContainer.svelte';
	import { ECategory } from '$lib/shared/enums/category.enum';
	import { EUserType } from '$lib/shared/enums/user-type.enum';
	import type { SelectInputOption } from '$lib/shared/interfaces/select-input-option';
	import { faArrowLeftLong, faArrowRight, faTrash } from '@fortawesome/free-solid-svg-icons';

	let selectedPlan = '';
	const planOptions: SelectInputOption[] = [
		{ label: 'Mensal', value: 'MONTHLY' },
		{ label: 'Três Meses', value: 'Three' },
		{ label: 'Seis Meses', value: 'SIX' },
		{ label: 'Anual', value: 'YEARLY' }
	];

	function handleSelect(value: string) {
		selectedPlan = value;
		console.log('Plano selecionado:', value);
	}
</script>

<div class="mt-6 flex flex-col items-center gap-4">
	<Button text={'Entrar'} onClick={() => {}} />
	<Button text={'Entrar'} onClick={() => {}} leftIcon={faTrash} />
	<Button text={'Entrar'} onClick={() => {}} rightIcon={faArrowRight} />
	<Input
		label={'Nome'}
		placeholder={'João'}
		width="300px"
		onInput={(value) => {
			console.log(value);
		}}
	/>
	<Input
		label={'Nome'}
		placeholder={'João'}
		width="300px"
		onInput={(value) => {
			console.log(value);
		}}
		error={'Nome inválido'}
	/>
	<Input
		placeholder="Pesquisar"
		width="500px"
		onInput={(value) => {
			console.log(value);
		}}
	/>
	<Input
		width="300px"
		onInput={(value) => {
			console.log(value);
		}}
		error={'Nome inválido'}
	/>
		<SelectInput
		label="Plano"
		bind:value={selectedPlan}
		placeholder="Selecione o plano"
		options={planOptions}
		onChange={handleSelect}
		width="300px"
		error="plano inválido"
	/>
	<ProfilePicture userName={'João'} />
	<ProfilePicture userName={'Felipe Scalco'} />
	<UserContainer email={'guilherme.123@gmail.com'} ageGroup={'Adulto'} />
	<UserContainer email={'josenildo.comisuamae.gamer@gmail.com'} ageGroup={'Criança'} isRpgMaster />
	<SelectInput
		label="Plano"
		bind:value={selectedPlan}
		placeholder="Selecione o plano"
		options={planOptions}
		onChange={handleSelect}
		width="300px"
	/>
	<div class="mt-6 flex flex-col items-start gap-4">
		<BoardGame title={'Catan: O Jogo'} category={ECategory.Negociacao} minParticipants={3} maxParticipants={4} />
		<BoardGame title={'Cidades Sombrias: Salem 1692'} category={ECategory.BlefeEnganacao} minParticipants={4} maxParticipants={12} isActivate={true} />
		<BoardGame title={'Daybreak'} category={ECategory.Cooperativos} minParticipants={1} maxParticipants={4} isActivate={true} />
		<BoardGame title={'Sherlock Holmes: Consulting Detective'} category={ECategory.Deducao} minParticipants={1} maxParticipants={8} />
		<BoardGame userType={EUserType.admin} title={'Dixit: Odyssey'} category={ECategory.PartyGame} minParticipants={3} maxParticipants={12} isActivate={true} />
		<BoardGame title={'Tá na mala'} category={ECategory.Destreza} minParticipants={2} maxParticipants={4} isActivate={true} />
		<BoardGame userType={EUserType.admin} title={'Hex'} category={ECategory.JogosAbstratos} minParticipants={2} isActivate={true} />
		<BoardGame title={'Mental Blocks'} category={ECategory.LogicaQuebraCabeca} minParticipants={2} maxParticipants={9} />
		<BoardGame userType={EUserType.admin} title={'Cthulhu: Death May Die'} category={ECategory.AventuraTematicos} minParticipants={1} maxParticipants={5} />
		<BoardGame title={'Inovatec: UTFPR'} category={ECategory.Outros} minParticipants={2} maxParticipants={4} isActivate={true} />
	</div>

	<!-- <div class="p-4 bg-gray-100">
		<h3 class="text-xl font-bold mb-4">Exemplo com Flexbox</h3>
		
		<div class="
			flex 
			flex-wrap 
			gap-4 
			md:gap-6 
			lg:gap-8
		">
			<div class="bg-sky-500 text-white font-bold p-6 rounded-lg flex-grow sm:flex-grow-0">Item 1</div>
			<div class="bg-sky-500 text-white font-bold p-6 rounded-lg flex-grow sm:flex-grow-0">Item 2</div>
			<div class="bg-sky-500 text-white font-bold p-6 rounded-lg flex-grow sm:flex-grow-0">Item 3</div>
			<div class="bg-sky-500 text-white font-bold p-6 rounded-lg flex-grow sm:flex-grow-0">Item 4</div>
			<div class="bg-sky-500 text-white font-bold p-6 rounded-lg flex-grow sm:flex-grow-0">Item 5</div>
		</div>
	</div> -->

</div>


<style>
	div {
		margin-bottom: 100px;
	}
</style>
