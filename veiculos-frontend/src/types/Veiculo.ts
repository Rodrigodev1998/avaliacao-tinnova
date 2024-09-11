export interface Veiculo {
  id: string;
  veiculo: string;
  marca: string;
  cor: string;
  ano: number;
  descricao: string;
  vendido: boolean;
  created: string;
  updated: string;
  }
  
  export interface RequestVeiculo {
    veiculo: string;
    marca: string;
    ano: number;
    cor: string;
    descricao: string;
    vendido: boolean;
  }