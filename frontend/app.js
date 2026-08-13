const BACKEND_URL = 'http://localhost:8080'; // Change this to your Render URL (e.g., https://my-game.onrender.com)

let sessionId = null;

const outputArea = document.getElementById('game-output');
const form = document.getElementById('input-form');
const inputField = document.getElementById('user-input');
const submitBtn = document.getElementById('submit-btn');
const restartBtn = document.getElementById('restart-btn');
const spinner = document.getElementById('loading-spinner');

function appendOutput(lines) {
    if (!lines) return;
    
    // Remove the initial spinner if it exists
    if (spinner.parentNode === outputArea) {
        outputArea.innerHTML = '';
    }

    lines.forEach(line => {
        // Strip out purely empty lines if they are excessive, but keep formatting
        const p = document.createElement('p');
        // colorize specific game phrases for aesthetics
        let formattedLine = line
            .replace(/===+/g, '<span style="color: var(--accent-color);">$&</span>')
            .replace(/---+/g, '<span style="color: var(--accent-hover);">$&</span>')
            .replace(/\*\*\* GAME OVER \*\*\*/, '<span style="color: #ef4444; font-weight: bold;">$&</span>')
            .replace(/Victory!/, '<span style="color: #22c55e; font-weight: bold;">$&</span>');
            
        p.innerHTML = formattedLine || '&nbsp;';
        outputArea.appendChild(p);
    });
    
    // Scroll to bottom
    outputArea.scrollTop = outputArea.scrollHeight;
}

function setLoading(isLoading) {
    inputField.disabled = isLoading;
    submitBtn.disabled = isLoading;
    if (isLoading) {
        spinner.classList.add('active');
        outputArea.appendChild(spinner);
        outputArea.scrollTop = outputArea.scrollHeight;
    } else {
        spinner.classList.remove('active');
        inputField.focus();
    }
}

async function startGame() {
    setLoading(true);
    outputArea.innerHTML = '';
    
    try {
        const response = await fetch(`${BACKEND_URL}/api/start`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' }
        });
        const data = await response.json();
        sessionId = data.sessionId;
        appendOutput(data.output);
    } catch (error) {
        appendOutput(['Error connecting to the server. Is the backend running?']);
        console.error(error);
    } finally {
        setLoading(false);
    }
}

form.addEventListener('submit', async (e) => {
    e.preventDefault();
    if (!sessionId) return;
    
    const input = inputField.value.trim();
    if (!input) return;
    
    inputField.value = '';
    appendOutput([`> ${input}`]); // echo user input
    setLoading(true);
    
    try {
        const response = await fetch(`${BACKEND_URL}/api/action`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ sessionId, input })
        });
        const data = await response.json();
        appendOutput(data.output);
    } catch (error) {
        appendOutput(['Error communicating with the server.']);
        console.error(error);
    } finally {
        setLoading(false);
    }
});

restartBtn.addEventListener('click', () => {
    startGame();
});

// Start game on load
window.addEventListener('DOMContentLoaded', () => {
    startGame();
});
