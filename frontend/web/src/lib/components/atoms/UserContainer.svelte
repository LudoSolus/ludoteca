<script lang="ts">
	import { faCrown, faUser } from '@fortawesome/free-solid-svg-icons';
	import Fa from 'svelte-fa';

	export let email: string;
	export let ageGroup: string;
	export let isRpgMaster: boolean = false;
    export let onClick: () => void = () => {};
</script>

<button class="flex items-center justify-between" on:click={onClick}>
	<div
		class="type-icon-box flex items-center justify-center"
		style="background-color: {isRpgMaster ? '#022780' : '#992C2C'};"
	>
		<Fa icon={isRpgMaster ? faCrown : faUser} color="#fff" size="1x" />
	</div>

	<p class="name-text">
		{email}
	</p>

	<p class="age-group-text">
		{ageGroup}
	</p>
</button>

<style>
	button {
		width: 450px;
		height: 50px;
		background-color: white;
		border: 2px solid #000;
		border-top-left-radius: 10px;
		border-bottom-left-radius: 10px;
		border-top-right-radius: 50px;
		border-bottom-right-radius: 50px;
		padding-right: 20px;
        cursor: pointer;
	}

	.type-icon-box {
		width: 60px;
		min-width: 60px;
		height: 100%;
	}

	.name-text {
		max-width: 280px;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}

	.age-group-text {
		font-size: 12px;
	}
</style>
