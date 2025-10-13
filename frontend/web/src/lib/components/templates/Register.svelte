<script lang="ts">
	import Button from '$lib/components/atoms/Button.svelte';
	import Input from '$lib/components/atoms/Input.svelte';
	import type { SelectInputOption } from '$lib/interfaces/select-input-option';
	import SelectInput from '../atoms/SelectInput.svelte';

	const instituteOptions: SelectInputOption[] = [
		{
			label: 'Universidade Tecnológica Federal do Paraná (UTFPR)',
			value: 'UTFPR'
		},
		{
			label: 'Universidade Estadual de Londrina (UEL)',
			value: 'UEL'
		}
	];
	let selectedInstitute = '';
</script>

<section class="flex h-full flex-col items-center justify-center gap-10 px-15 py-4">
	<h1>Para começar sua jornada <br /> Crie uma conta</h1>
	<div class="form-container flex w-full flex-wrap items-start justify-center gap-5">
		<Input
			label={'Nome Completo'}
			placeholder={'João dos Santos'}
			width="300px"
			onInput={(value) => {
				console.log(value);
			}}
		/>
		<Input
			label={'Telefone'}
			placeholder={'(43) 99999-9999'}
			mask="phone"
			width="300px"
			height="90px"
			onInput={(value) => {
				console.log(value);
			}}
		/>
		<Input
			label={'E-mail'}
			placeholder={'joao@gmail.com'}
			type="email"
			width="300px"
			height="90px"
			onInput={(value) => {
				console.log(value);
			}}
		/>
		<Input
			label={'CPF'}
			placeholder={'000.000.000-00'}
			mask="cpf"
			width="300px"
			height="90px"
			onInput={(value) => {
				console.log(value);
			}}
		/>
		<Input
			label={'Data de Nascimento'}
			type="date"
			width="300px"
			height="90px"
			onInput={(value) => {
				console.log(value);
			}}
		/>
		<SelectInput
			label={'Instituição'}
			placeholder={'Selecione sua instituição'}
			width="300px"
			height="90px"
			options={instituteOptions}
			onChange={(value) => {
				selectedInstitute = value;
				console.log(value);
			}}
			bind:value={selectedInstitute}
		/>
		<Input
			label={'Senha'}
			type="password"
			width="300px"
			height="90px"
			onInput={(value) => {
				console.log(value);
			}}
		/>
		<Input
			label={'RA'}
			placeholder={'0000000'}
			mask="ra"
			width="300px"
			height="90px"
			onInput={(value) => {
				console.log(value);
			}}
		/>
	</div>
	<Button text="Criar Conta" onClick={() => {}} />
</section>

<style>
	section {
		background: var(--card-background-color);
		border-top-left-radius: 20px;
		border-bottom-left-radius: 20px;
		box-shadow: -2px 0px 4px 0px rgba(0, 0, 0, 0.7);
	}

	h1 {
		width: 100%;
		font-size: 36px;
		font-weight: 900;
	}

	.form-container {
		min-width: 620px;
		max-width: 620px;
	}
</style>
