const API_URL = 'http://localhost:8080/api/todos';

async function loadTasks() {
    try {
        const response = await fetch(API_URL);
        const tasks = await response.json();
        renderTasks(tasks);
    } catch (error) {
        showError('Failed to load tasks');
    }
}

function renderTasks(tasks) {
    const taskList = document.getElementById('taskList');
    taskList.innerHTML = '';

    if (tasks.length === 0) {
        taskList.innerHTML = '<li class="list-group-item text-muted">No tasks yet</li>';
        return;
    }

    tasks.forEach(task => {
        const li = document.createElement('li');
        li.className = 'list-group-item d-flex justify-content-between align-items-center';
        
        const textClass = task.done ? 'text-decoration-line-through text-muted' : '';
        
        li.innerHTML = `
            <span class="${textClass}">${task.title}</span>
            <div>
                <button class="btn btn-sm btn-outline-success me-2" onclick="toggleTask(${task.id})">
                    ${task.done ? 'Undo' : 'Done'}
                </button>
                <button class="btn btn-sm btn-outline-danger" onclick="deleteTask(${task.id})">
                    Delete
                </button>
            </div>
        `;
        
        taskList.appendChild(li);
    });
}

async function addTask() {
    const input = document.getElementById('taskInput');
    const title = input.value.trim();

    if (title.length < 3) {
        showError('Title must be at least 3 characters');
        return;
    }

    try {
        const response = await fetch(API_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ title })
        });

        if (response.ok) {
            input.value = '';
            hideError();
            loadTasks();
        } else {
            const error = await response.json();
            showError(error.error || 'Failed to add task');
        }
    } catch (error) {
        showError('Failed to add task');
    }
}

async function toggleTask(id) {
    try {
        const response = await fetch(`${API_URL}/${id}/toggle`, {
            method: 'PUT'
        });

        if (response.ok) {
            loadTasks();
        } else {
            const error = await response.json();
            showError(error.error || 'Task not found');
        }
    } catch (error) {
        showError('Failed to toggle task');
    }
}

async function deleteTask(id) {
    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            loadTasks();
        } else {
            const error = await response.json();
            showError(error.error || 'Task not found');
        }
    } catch (error) {
        showError('Failed to delete task');
    }
}

function showError(message) {
    const errorDiv = document.getElementById('error');
    errorDiv.textContent = message;
    errorDiv.style.display = 'block';
}

function hideError() {
    const errorDiv = document.getElementById('error');
    errorDiv.style.display = 'none';
}

document.getElementById('taskInput').addEventListener('keypress', (e) => {
    if (e.key === 'Enter') {
        addTask();
    }
});

loadTasks();