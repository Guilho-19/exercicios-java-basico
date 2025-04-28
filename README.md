# Exercícios

## todos os execicios devem ter um menu de interativo para chamar as funções e ter uma opção de sair para finalizar a execução

1. Escreva um código onde temos uma conta bancaria que possa realizar as seguintes operações:
    - Consultar saldo
    - consultar cheque especial
    - Depositar dinheiro;
    - Sacar dinheiro;
    - Pagar um boleto.
    - Verificar se a conta está usando cheque especial.

Siga as seguintes regras para implementar
   - A conta bancária deve ter um limite de cheque especial somado ao saldo da conta;
   - O o valor do cheque especial é definido no momento da criação da conta, de acordo com o valor depositado na conta em sua criação;
   - Se o valor depositado na criação da conta for de R$500,00 ou menos o cheque especial deve ser de R$50,00
   - Para valores acima de R$500,00 o cheque especial deve ser de 50% do valor depositado;
   - Caso o limite de cheque especial seja usado, assim que possível a conta deve cobrar uma taxa de 20% do valor usado do cheque especial.

## Resolução Exercício 1

```java
package exercicios.java.basico.exercicio01;

import java.util.Scanner;

public class ContaBancaria {
	private Scanner scanner = new Scanner(System.in);
	private double chequeEspecial;
	private double valorUsarChequeEspecial = 0;
	private double saldo = 0;
	
	public static void main(String[] args) {
		ContaBancaria conta = new ContaBancaria();
		conta.menu();
	}
	
	private void menu() {
		int menuEscolhido;
		
		do {
			System.out.println("=== BANCO DIGITAL ===");
			System.out.println("\nMENU PRINCIPAL");
			System.out.println("1 - Consultar saldo");
			System.out.println("2 - Realizar depósito");
			System.out.println("3 - Realizar saque");
			System.out.println("4 - Pagar boleto");
			System.out.println("5 - Realizar primeiro depósito");
			System.out.println("0 - Sair");
			
			while (!scanner.hasNextInt()) {
				System.out.println("Por favor, digite um número válido!");
				scanner.next();
			}
			
			menuEscolhido = scanner.nextInt();
		
			switch (menuEscolhido) {
			case 1 -> consultarSaldo();
			case 2 -> deposito();
			case 3 -> saque();
			case 4 -> pagarBoleto();
			case 5 -> calculoChequeEspecial();
			case 0 -> System.out.println("Obrigado por usar nossos serviços!");
			default -> System.out.println("Opção inválida");
			}
		} while (menuEscolhido != 0);
	}
	
	private void consultarSaldo() {
		System.out.println("O valor do saldo atual da sua conta é de R$" + saldo);
		if (valorUsarChequeEspecial > 0) {
			System.out.println("Você está utilizando cheque especial no valor de R$" + valorUsarChequeEspecial);
		}
	}
	
	private void calculoChequeEspecial() {
		if (saldo == 0) {
			System.out.println("Qual será o valor do seu depósito inicial?");
			double depositoInicial = scanner.nextDouble();
			
			if (depositoInicial <= 500) {
				saldo += depositoInicial;
				chequeEspecial = 50;
				System.out.println("Depósito no valor de R$" + depositoInicial + " feito com sucesso");
				System.out.println("Seu cheque especial é de R$" + chequeEspecial);
			} else if (depositoInicial > 500) {
				chequeEspecial = depositoInicial * 0.50;
				saldo += depositoInicial;
				System.out.println("Depósito no valor de R$" + depositoInicial + " feito com sucesso");
				System.out.println("Seu cheque especial é de R$" + chequeEspecial);
			}
		} else {
			System.out.println("Você já realizou seu primeiro depósito");
		}
	}
	
	private void deposito() {
		System.out.println("Qual será o valor do seu depósito?");
		double valorDeposito = scanner.nextDouble();
		
		if (valorDeposito <= 0) {
			System.out.println("Valor de depósito inválido");
		} else {
			if (valorUsarChequeEspecial > 0) {
				if (valorDeposito >= valorUsarChequeEspecial) {
					double restanteDeposito = valorDeposito - valorUsarChequeEspecial;
	                System.out.println("R$" + valorUsarChequeEspecial + " do seu depósito foi usado para quitar o cheque especial");
	
	                chequeEspecial += valorUsarChequeEspecial;
	                valorUsarChequeEspecial = 0;
	
	                saldo += restanteDeposito;
	                System.out.println("Depósito no valor de R$" + valorDeposito + " processado com sucesso");
	                System.out.println("R$" + restanteDeposito + " foi adicionado ao seu saldo");
				} else {
					valorUsarChequeEspecial -= valorDeposito;
					chequeEspecial += valorDeposito;
					
					System.out.println("Seu depósito de R$" + valorDeposito + " foi integralmente utilizado para reduzir sua dívida de cheque especial");
					System.out.println("Você ainda está utilizando R$" + valorUsarChequeEspecial + " do cheque especial");
				}
			} else {
				saldo += valorDeposito;
				System.out.println("Depósito no valor de R$" + valorDeposito + " efetuado com sucesso");
				System.out.println("Seu novo saldo é de R$" + saldo);
			}
		}
	}
	
	private void saque() {
		System.out.println("Qual será o valor do seu saque?");
		double valorSaque = scanner.nextDouble();
		
		if (valorSaque <= 0) {
			System.out.println("Valor de saque inválido");
		} else {
			if (valorSaque <= saldo) {
				saldo -= valorSaque;
				System.out.println("Saque no valor de R$" + valorSaque + " efetuado com sucesso");
				System.out.println("Seu novo saldo é de R$" + saldo);
			} else if (valorSaque <= (saldo + chequeEspecial)){
				valorUsarChequeEspecial = valorSaque - saldo;
				double taxa = valorUsarChequeEspecial * 0.2;
				double totalDebitado = valorUsarChequeEspecial + taxa;
				
				saldo = 0;
				chequeEspecial -= totalDebitado;
				valorUsarChequeEspecial += totalDebitado;
				
				System.out.println("Saque no valor de R$" + valorSaque + " efetuado com sucesso");
				System.out.println("Seu novo saldo é de R$" + saldo);
				System.out.println("Você entrou no cheque especial");
			} else {
				System.out.println("Saldo insuficiente para concluir a transação");
				System.out.println("Seu saldo (incluindo cheque especial): R$" + (saldo + chequeEspecial));
			}
		}
	}
	
	private void pagarBoleto() {
		System.out.println("Qual será o valor do boleto que você irá pagar?");
		double valorBoleto = scanner.nextDouble();
		
		if (valorBoleto <= 0) {
			System.out.println("Valor do boleto inválido");
		} else {
			if (valorBoleto <= saldo) {
				saldo = saldo - valorBoleto;
				System.out.println("Pagamento do boleto no valor de R$" + valorBoleto + " efetuado com sucesso");
				System.out.println("Seu novo saldo é de R$" + saldo);
			} else if (valorBoleto <= (saldo + chequeEspecial)){
				valorUsarChequeEspecial = valorBoleto - saldo;
				double taxa = valorUsarChequeEspecial * 0.2;
				double totalDebitado = valorUsarChequeEspecial + taxa;
				
				saldo = 0;
				chequeEspecial -= totalDebitado;
				valorUsarChequeEspecial += totalDebitado;
				
				System.out.println("Boleto no valor de R$" + valorBoleto + " pago com sucesso");
				System.out.println("Seu novo saldo é de R$" + saldo);
				System.out.println("Você entrou no cheque especial");
			} else {
				System.out.println("Saldo insuficiente para concluir a transação");
				System.out.println("Seu saldo (incluindo cheque especial): R$" + (saldo + chequeEspecial));
			}
		}	
	}
}
