document.addEventListener('DOMContentLoaded', function() {
    const userId = localStorage.getItem('userId');
    if (!userId) {
        alert('Usuário não logado!');
        window.location.href = '../login/index.html'; // Redireciona para a página de login
        return;
    }
    document.getElementById('newImageButton').style.display = 'none';
    document.getElementById('loading').style.display = 'none';
});

function previewImage(event) {
    const input = event.target;
    const previewImg = document.getElementById('previewImg');
    const resultDiv = document.getElementById('result');
    resultDiv.innerText = ''; // Clear previous result

    if (input.files && input.files[0]) {
        const reader = new FileReader();
        reader.onload = function(e) {
            previewImg.src = e.target.result;
        };
        reader.readAsDataURL(input.files[0]);
    } else {
        previewImg.src = '';
    }
}

async function identifyMovie() {
    const input = document.getElementById('imageUpload');
    const identifyButton = document.getElementById('identifyButton');
    const loadingDiv = document.getElementById('loading');
    const resultDiv = document.getElementById('result');
    const resultContainer = document.getElementById('resultContainer');
    const newImageButton = document.getElementById('newImageButton');

    if (input.files.length === 0) {
        alert('Por favor, selecione uma imagem.');
        return;
    }

    identifyButton.style.display = 'none';
    loadingDiv.style.display = 'flex';
    resultDiv.innerText = '';
    resultContainer.style.display = 'none';

    const file = input.files[0];
    const reader = new FileReader();
    reader.onloadend = async function () {
        const arrayBuffer = reader.result;

        const response = await fetch('https://treinocinesearch-prediction.cognitiveservices.azure.com/customvision/v3.0/Prediction/c291b533-d5c4-4f82-a9b3-0cf0bc53330d/classify/iterations/Iteration1/image', {
            method: 'POST',
            headers: {
                'Prediction-Key': 'd22afa408b9e4c21b3eef459cd9910eb',
                'Content-Type': 'application/octet-stream'
            },
            body: arrayBuffer
        });

        const result = await response.json();
        loadingDiv.style.display = 'none';
        resultContainer.style.display = 'flex';
        newImageButton.style.display = 'block';

        if (result.predictions && result.predictions.length > 0) {
            resultDiv.innerText = `Filme: ${result.predictions[0].tagName}`;
        } else {
            resultDiv.innerText = 'Filme não reconhecido';
        }
    };
    reader.readAsArrayBuffer(file);
}

function reset() {
    document.getElementById('imageUpload').value = '';
    document.getElementById('previewImg').src = '';
    document.getElementById('identifyButton').style.display = 'block';
    document.getElementById('loading').style.display = 'none';
    document.getElementById('resultContainer').style.display = 'none';
    document.getElementById('newImageButton').style.display = 'none';
    document.getElementById('result').innerText = '';
}
