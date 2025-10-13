<script lang="ts">
	import Button from '$lib/components/atoms/Button.svelte';
	import Input from '$lib/components/atoms/Input.svelte';
	import ProfilePicture from '$lib/components/atoms/ProfilePicture.svelte';
	import SelectInput from '$lib/components/atoms/SelectInput.svelte';
	import UserContainer from '$lib/components/atoms/UserContainer.svelte';
	import type { SelectInputOption } from '$lib/interfaces/select-input-option';
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
</div>

<style>
	div {
		margin-bottom: 100px;
	}
</style>
