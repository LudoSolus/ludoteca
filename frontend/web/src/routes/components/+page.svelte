<script>
	import Button from '$lib/components/atoms/Button.svelte';
	import Input from '$lib/components/atoms/Input.svelte';
	import { faArrowLeftLong, faArrowRight, faTrash } from '@fortawesome/free-solid-svg-icons';
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
</div>

<style>
</style>
