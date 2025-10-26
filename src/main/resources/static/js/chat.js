marked.setOptions({ breaks:true, gfm:true });

let isFirstMessage = true;

async function sendMessage() {
    const input = document.getElementById('user-input');
    const message = input.value.trim();
    if (!message) return;

    const sendBtn = document.getElementById('send-btn');
    sendBtn.disabled = true;

    if (isFirstMessage) {
        const empty = document.querySelector('.empty-state');
        if (empty) empty.remove();
        isFirstMessage = false;
    }

    appendMessage('user', message);
    input.value = '';
    input.style.height = 'auto';

    try {
        const response = await fetch('/api/chat', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ message })
        });

        const text = await response.text();
        appendMessage('bot', text);
    } catch (error) {
        appendMessage('bot', 'Sorry, there was an error processing your request.');
    }

    sendBtn.disabled = false;
}

function appendMessage(sender, message) {
    const chatBox = document.getElementById('chat-box');
    const msgDiv = document.createElement('div');
    msgDiv.classList.add('message', sender);

    const avatar = document.createElement('div');
    avatar.classList.add('avatar', sender);
    avatar.textContent = sender === 'user' ? 'Y' : 'Z';

    const content = document.createElement('div');
    content.classList.add('message-content');
    
    if (sender === 'bot') {
        content.innerHTML = marked.parse(message);
    } else {
        content.textContent = message;
    }

    msgDiv.appendChild(avatar);
    msgDiv.appendChild(content);
    chatBox.appendChild(msgDiv);
    chatBox.scrollTop = chatBox.scrollHeight;
}

function handleKeyPress(event) {
    if (event.key === 'Enter' && !event.shiftKey) {
        event.preventDefault();
        sendMessage();
    }
}

document.getElementById('user-input').addEventListener('input', function() {
    this.style.height = 'auto';
    this.style.height = Math.min(this.scrollHeight, 200) + 'px';
});
