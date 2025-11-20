<script lang="ts">
	import BoardGame from '$lib/components/atoms/BoardGame.svelte';
	import Button from '$lib/components/atoms/Button.svelte';
	import Input from '$lib/components/atoms/Input.svelte';
	import ProfilePicture from '$lib/components/atoms/ProfilePicture.svelte';
	import SelectInput from '$lib/components/atoms/SelectInput.svelte';
	import UserContainer from '$lib/components/atoms/UserContainer.svelte';
	import { ECategory } from '$lib/shared/enums/category.enum';
	import { EUserType } from '$lib/shared/enums/user-type.enum';
	import { faArrowRight, faTrash } from '@fortawesome/free-solid-svg-icons';
	import EscapeRoomContainer from '$lib/components/atoms/EscapeRoomContainer.svelte';
	import RpgContainer from '$lib/components/atoms/RpgContainer.svelte';
	import GoBack from '$lib/components/molecules/GoBack.svelte';
	let eventDate: Date = new Date("2025-09-05");
	
	type SelectInputOption = {
		label: string;
		value: string;
	};

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

<RpgContainer title="Era Glacial de Ragnar" system="D&D" startDate={eventDate} />

<div class="mt-6 flex flex-col items-center gap-4">
	<div class="w-full px-2 sm:px-5 md:px-10">
		<GoBack title="Perfil" description="Todos os personagens que participam dessa campanha" type="admin" />
	</div>
	<div class="w-full px-2 sm:px-5 md:px-10">
		<GoBack title="Perfil" description="Tela de perfil, veja suas informações" type="user" />
	</div>
	<EscapeRoomContainer title="Código Sombrio" />
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

</div>

<style>
	div {
		margin-bottom: 100px;
	}
</style>
