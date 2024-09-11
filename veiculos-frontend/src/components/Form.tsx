import { useState } from "react";
import { Veiculo } from "../types/Veiculo";
import { Button, Notification, Radio, RadioGroup, TextInput } from "@mantine/core";
import './Form.css';

interface FormularioProps {
    onVeiculoCreated: () => void;
}
export default function Formulario({ onVeiculoCreated }: FormularioProps) {
    const [formValues, setFormValues] = useState<Partial<Veiculo>>({});
    const [error, setError] = useState<string | null>(null);
    const [success, setSuccess] = useState<string | null>(null);
    const apiUrl = import.meta.env.VITE_API_URL;

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormValues((prevValues) => ({ ...prevValues, [name]: value }));
    };

    const handleRadioChange = (value: string) => {
        setFormValues((prevValues) => ({ ...prevValues, vendido: value === 'true' }));
    };

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        try {
            const response = await fetch(`${apiUrl}/veiculos`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formValues),
            });

            if (!response.ok) {
                throw new Error('Erro ao criar veículo');
            }

            setSuccess('Veículo criado com sucesso!');
            setFormValues({});
            onVeiculoCreated();
        } catch (error) {
            console.error('Erro ao criar veículo:', error);
            setError('Erro ao criar veículo');
        }
    };

    return (
        <div>
            <h2>Cadastrar veiculo</h2>
            {error && <Notification color="red">{error}</Notification>}
            <form onSubmit={handleSubmit}>
                <div className="area-input">
                    <TextInput
                        label="Veículo"
                        name="veiculo"
                        value={formValues.veiculo || ''}
                        onChange={handleChange}
                        required
                    />
                    <TextInput
                        label="Marca"
                        name="marca"
                        value={formValues.marca || ''}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div className="area-input">
                    <TextInput
                        label="Veículo"
                        name="veiculo"
                        value={formValues.veiculo || ''}
                        onChange={handleChange}
                        required
                    />
                    <TextInput
                        label="Marca"
                        name="marca"
                        value={formValues.marca || ''}
                        onChange={handleChange}
                        required
                    />
                    
                </div>
                <div className="area-input">
                    <TextInput
                        label="Cor"
                        name="cor"
                        value={formValues.cor || ''}
                        onChange={handleChange}
                        required
                     />
                    <TextInput
                        label="Ano"
                        name="ano"
                        type="number"
                        value={formValues.ano || ''}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div className="area-input">
                    <TextInput
                        label="Descrição"
                        name="descricao"
                        value={formValues.descricao || ''}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label>Vendido</label>
                    <RadioGroup
                        value={formValues.vendido ? 'true' : 'false'}
                        onChange={handleRadioChange}
                    >
                        <Radio value="true" label="Sim" className="btn-vendido"/>
                        <Radio value="false" label="Não" />
                    </RadioGroup>
                </div>
                <br />
                    <Button type="submit">Cadastrar Veículo</Button>
            </form>
        </div>
    );
}