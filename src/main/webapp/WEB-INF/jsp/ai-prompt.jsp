<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>AI Model Prompt Comparator</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        /* Global styles */
        body {
            font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
            line-height: 1.4;
            color: #374151;
            background-color: #f9fafb;
            margin: 0;
            padding: 10px;
            max-width: 1200px;
            margin: 0 auto;
            font-size: 0.9rem;
        }

        h3 {
            color: #1f2937;
            font-weight: 600;
            margin-top: 0.75rem;
            margin-bottom: 0.5rem;
            font-size: 0.95rem;
        }

        label {
            font-weight: 500;
            color: #4b5563;
            display: block;
            margin-bottom: 0.25rem;
            font-size: 0.85rem;
        }

        input[type="number"], 
        select {
            width: 100%;
            max-width: 300px;
            padding: 0.4rem;
            border: 1px solid #d1d5db;
            border-radius: 0.25rem;
            background-color: white;
            font-size: 0.85rem;
            color: #1f2937;
            transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
        }

        input[type="number"]:focus, 
        select:focus,
        textarea:focus {
            outline: none;
            border-color: #d1c8c0;
            box-shadow: 0 0 0 2px rgba(209, 200, 192, 0.25);
        }

        input[type="submit"] {
            background-color: #8e6d64;
            color: white;
            border: none;
            padding: 0.4rem 1rem;
            border-radius: 0.25rem;
            font-weight: 500;
            cursor: pointer;
            transition: background-color 0.15s ease-in-out;
            font-size: 0.85rem;
        }

        input[type="submit"]:hover {
            background-color: #7d5f57;
        }

        hr {
            border: 0;
            height: 1px;
            background-color: #e5e7eb;
            margin: 2rem 0;
        }

        /* Dropdown styles */
        #dropdown {
            position: absolute;
            background-color: white;
            border: 1px solid #d1d5db;
            border-radius: 0.375rem;
            display: none;
            z-index: 1000;
            width: 200px;
            max-height: 300px;
            overflow-y: auto;
            box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
        }

        #dropdown div {
            padding: 0.75rem;
            cursor: pointer;
            transition: background-color 0.15s ease-in-out;
        }

        #dropdown div:hover {
            background-color: #f3f4f6;
        }

        .labelDropdown {
            position: absolute;
            background-color: white;
            border: 1px solid #d1d5db;
            border-radius: 0.375rem;
            display: none;
            z-index: 1001;
            width: 200px;
            box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
        }

        .labelDropdown div {
            padding: 0.4rem 0.75rem;
            cursor: pointer;
            transition: background-color 0.15s ease-in-out;
            line-height: 1.2;
        }

        .labelDropdown div:hover {
            background-color: #f3f4f6;
        }

        /* Prompt styles */
        #promptWrapper {
            position: relative;
            margin-bottom: 1.5rem;
        }

        #promptMirror {
            white-space: pre-wrap;
            word-wrap: break-word;
            position: absolute;
            top: 0;
            left: 0;
            pointer-events: none;
            color: transparent;
        }

        textarea {
            position: relative;
            background: transparent;
            z-index: 1;
            width: 100%;
            padding: 0.5rem;
            border: 1px solid #d1d5db;
            border-radius: 0.25rem;
            font-size: 0.85rem;
            color: #1f2937;
            min-height: 100px;
            resize: vertical;
            font-family: inherit;
        }

        /* Model selection styles */
        .model-selection {
            margin-bottom: 0.5rem;
            display: flex;
            align-items: center;
        }

        .model-selection label {
            display: inline-block;
            margin-bottom: 0;
            margin-right: 0.5rem;
        }

        .model-selection select {
            flex: 1;
            max-width: 300px;
            padding: 0.25rem;
            font-size: 0.85rem;
        }

        /* Parameter styles */
        .parameter-group {
            display: flex;
            flex-wrap: wrap;
            gap: 1.5rem;
            margin-bottom: 1.5rem;
        }

        .parameter-item {
            flex: 1;
            min-width: 200px;
        }

        /* Answers container styles */
        .answers-container {
            display: flex;
            flex-direction: row;
            flex-wrap: wrap;
            gap: 0.75rem;
            margin-top: 0.25rem;
            width: 100%;
        }

        .model-answer {
            border: 1px solid #e5e7eb;
            border-radius: 0.5rem;
            padding: 0.75rem;
            background-color: white;
            flex: 1;
            min-width: 250px;
            box-sizing: border-box;
            box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.1), 0 1px 2px 0 rgba(0, 0, 0, 0.06);
            transition: transform 0.2s ease-in-out, box-shadow 0.2s ease-in-out;
        }

        .model-answer:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
        }

        .model-answer h4 {
            margin-top: 0;
            padding-bottom: 0.75rem;
            border-bottom: 1px solid #e5e7eb;
            color: #8e6d64;
            font-weight: 600;
        }

        /* Tooltip styles */
        [data-tooltip] {
            position: relative;
            cursor: help;
        }

        [data-tooltip]::after {
            content: attr(data-tooltip);
            position: absolute;
            bottom: 125%;
            left: 50%;
            transform: translateX(-50%);
            width: 150px; /* Fixed width, doubled from estimated 150px */
            height: auto;
            padding: 0.75rem;
            border-radius: 0.375rem;
            background-color: white;
            color: #374151;
            font-size: 0.8em; /* Decreased by half from 0.8rem */
            line-height: 1.5;
            text-align: left;
            box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
            border: 1px solid #e5e7eb;
            opacity: 0;
            visibility: hidden;
            transition: opacity 0.3s, visibility 0.3s;
            z-index: 1000;
            pointer-events: none;
            white-space: normal;
            word-wrap: break-word;
            overflow-wrap: break-word;
            overflow: visible;
        }

        [data-tooltip]:hover::after {
            opacity: 1;
            visibility: visible;
        }

        /* Loading spinner styles */
        .loading-spinner {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(249, 250, 251, 0.8);
            z-index: 9999;
            justify-content: center;
            align-items: center;
        }

        .spinner {
            width: 50px;
            height: 50px;
            border: 4px solid rgba(142, 109, 100, 0.2);
            border-top: 4px solid #8e6d64;
            border-radius: 50%;
            animation: spin 1s linear infinite;
        }

        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }
    </style>
</head>
<body>
<!-- Loading spinner overlay -->
<div class="loading-spinner" id="loadingSpinner">
    <div class="spinner"></div>
</div>

<header style="margin-bottom: 0.5rem; border-bottom: 1px solid #e5e7eb; padding-bottom: 0.5rem; margin-top: 0.5rem;">
    <h1 style="color: #8e6d64; font-weight: 700; font-size: 1.6rem; margin-bottom: 0.25rem;">AI Model Prompt Comparator</h1>
    <p style="color: #6b7280; font-size: 0.9rem; max-width: 700px;"></p>
</header>

<form id="aiForm" action="${pageContext.request.contextPath}/ai/generate" method="post">
    <div style="margin-bottom: 0.75rem;">
        <label for="prompt" style="font-size: 0.95rem; font-weight: 600;"></label>
        <p style="color: #6b7280; margin-top: 0.15rem; margin-bottom: 0.5rem; font-size: 0.85rem;">Type your question or prompt below. Use '@' to access parameter suggestions.</p>

        <div id="promptWrapper" style="border-radius: 0.5rem; box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.1), 0 1px 2px 0 rgba(0, 0, 0, 0.06);">
            <div id="promptMirror"></div>
            <textarea id="prompt" name="prompt" rows="5" cols="60" placeholder="Enter your prompt here...">${promptDTO.prompt}</textarea>
        </div>
        <div id="dropdown"></div>
    </div>

    <div style="margin-bottom: 1rem;">
        <label style="font-size: 0.95rem; font-weight: 600;">AI Models</label>
        <p style="color: #6b7280; margin-top: 0.05rem; margin-bottom: 0.5rem; font-size: 0.85rem;">Select up to three AI models to compare responses from different or the same systems.</p>

        <div style="background-color: #f9fafb; border-radius: 0.5rem; padding: 0.75rem; border: 1px solid #e5e7eb;">
            <div class="model-selection">
                <label for="model1">
                    <div style="width: 24px; height: 24px; border-radius: 50%; background-color: #8e6d64; display: inline-flex; align-items: center; justify-content: center; margin-right: 0.5rem;">
                        <span style="color: white; font-weight: bold; font-size: 0.875rem;">1</span>
                    </div>
                </label>
                <select id="model1" name="models">
                    <option value="">-- Select Model Type --</option>
                    <c:forEach items="${modelTypes}" var="modelType">
                        <option value="${modelType}" ${promptDTO.models != null && promptDTO.models.size() > 0 && promptDTO.models[0] == modelType ? 'selected' : ''}>${modelType}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="model-selection">
                <label for="model2">
                    <div style="width: 24px; height: 24px; border-radius: 50%; background-color: #8e6d64; display: inline-flex; align-items: center; justify-content: center; margin-right: 0.5rem;">
                        <span style="color: white; font-weight: bold; font-size: 0.875rem;">2</span>
                    </div>
                </label>
                <select id="model2" name="models">
                    <option value="">-- Select Model Type --</option>
                    <c:forEach items="${modelTypes}" var="modelType">
                        <option value="${modelType}" ${promptDTO.models != null && promptDTO.models.size() > 1 && promptDTO.models[1] == modelType ? 'selected' : ''}>${modelType}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="model-selection">
                <label for="model3">
                    <div style="width: 24px; height: 24px; border-radius: 50%; background-color: #8e6d64; display: inline-flex; align-items: center; justify-content: center; margin-right: 0.5rem;">
                        <span style="color: white; font-weight: bold; font-size: 0.875rem;">3</span>
                    </div>
                </label>
                <select id="model3" name="models">
                    <option value="">-- Select Model Type --</option>
                    <c:forEach items="${modelTypes}" var="modelType">
                        <option value="${modelType}" ${promptDTO.models != null && promptDTO.models.size() > 2 && promptDTO.models[2] == modelType ? 'selected' : ''}>${modelType}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </div>

    <div style="margin-bottom: 1rem;">
        <label style="font-size: 0.95rem; font-weight: 600;">Advanced Parameters</label>
        <p style="color: #6b7280; margin-top: 0.15rem; margin-bottom: 0.5rem; font-size: 0.85rem;">Fine-tune the AI response by adjusting these parameters.</p>

        <div style="background-color: #f9fafb; border-radius: 0.5rem; padding: 0.75rem; border: 1px solid #e5e7eb;">
            <div style="display: flex; flex-wrap: wrap; gap: 0.75rem; align-items: center;">
                <div style="display: flex; align-items: center; gap: 0.5rem;">
                    <label for="temperature" style="font-size: 0.85rem; margin: 0; white-space: nowrap;" data-tooltip="Controls randomness: Lower values (e.g., 0.2) make responses more focused and deterministic, while higher values (e.g., 0.8) make output more random and creative. Valid range: 0 (minimum) to 2 (maximum).">Temperature:</label>
                    <input type="number" id="temperature" name="temperature" step="0.1" min="0" max="2" value="${promptDTO.temperature != null ? promptDTO.temperature : commonConfig.temperature}" style="width: 60px; padding: 0.25rem; font-size: 0.85rem;" />
                </div>

                <div style="display: flex; align-items: center; gap: 0.5rem;">
                    <label for="maxTokens" style="font-size: 0.85rem; margin: 0; white-space: nowrap;" data-tooltip="The maximum length of the response in tokens (roughly 4 characters per token). Higher values allow for longer responses but may increase processing time. Valid range: 1 (minimum) to 8192 (maximum).">Max Tokens:</label>
                    <input type="number" id="maxTokens" name="maxTokens" min="1" max="8192" value="${promptDTO.maxTokens != null ? promptDTO.maxTokens : commonConfig.maxTokens}" style="width: 70px; padding: 0.25rem; font-size: 0.85rem;" />
                </div>

                <div style="display: flex; align-items: center; gap: 0.5rem;">
                    <label for="topP" style="font-size: 0.85rem; margin: 0; white-space: nowrap;" data-tooltip="Controls diversity by limiting the cumulative probability of token selection. Lower values (e.g., 0.5) make the output more focused, while higher values (e.g., 0.9) make it more diverse. Valid range: 0 (minimum) to 1 (maximum).">Top P:</label>
                    <input type="number" id="topP" name="topP" step="0.1" min="0" max="1" value="${promptDTO.topP != null ? promptDTO.topP : commonConfig.topP}" style="width: 60px; padding: 0.25rem; font-size: 0.85rem;" />
                </div>
            </div>
        </div>
    </div>

    <input type="submit" value="Ask AI" style="font-size: 0.95rem; padding: 0.5rem 1.5rem;">
</form>

<hr style="margin: 0.75rem 0;">

<div style="margin-top: 0.75rem;">
    <c:if test="${promptDTO != null && not empty promptDTO.prompt}">
        <div style="background-color: #f9fafb; border-radius: 0.5rem; padding: 0.75rem; border: 1px solid #e5e7eb; margin-bottom: 0.75rem;">
            <h3 style="color: #8e6d64; margin-top: 0; margin-bottom: 0.5rem; font-size: 0.95rem;">Your Prompt</h3>
            <p style="margin: 0; line-height: 1.4; font-size: 0.85rem;">${promptDTO.prompt}</p>
        </div>
    </c:if>

    <c:if test="${promptDTO != null && promptDTO.models != null && promptDTO.answers != null}">
        <h3 style="color: #1f2937; font-weight: 600; margin-bottom: 0.5rem; font-size: 1rem;">AI Responses</h3>
        <div class="answers-container">
            <c:forEach items="${promptDTO.models}" var="model" varStatus="status">
                <c:if test="${status.index < promptDTO.answers.size()}">
                    <div class="model-answer">
                        <div style="display: flex; align-items: center; margin-bottom: 0.5rem;">
                            <div style="width: 20px; height: 20px; border-radius: 50%; background-color: #8e6d64; margin-right: 0.5rem; display: flex; align-items: center; justify-content: center;">
                                <span style="color: white; font-weight: bold; font-size: 0.75rem;">${status.index + 1}</span>
                            </div>
                            <h4 style="margin: 0; color: #8e6d64; font-size: 0.9rem;">${model}</h4>
                        </div>
                        <div style="padding: 0.5rem; background-color: #f9fafb; border-radius: 0.375rem; border: 1px solid #e5e7eb;">
                            <pre style="white-space: pre-wrap; font-family: inherit; margin: 0; line-height: 1.4; font-size: 0.85rem;">${promptDTO.answers[status.index]}</pre>
                        </div>
                    </div>
                </c:if>
            </c:forEach>
        </div>
    </c:if>
</div>

<c:set var="paramJson" value="${parameterDisplayValues}" />

<script>
    const parameterMap = JSON.parse('${paramJson}');

    const prompt = document.getElementById("prompt");
    const dropdown = document.getElementById("dropdown");

    prompt.addEventListener("keyup", function (e) {
        const cursorPos = prompt.selectionStart;
        const text = prompt.value;
        const beforeCursor = text.slice(0, cursorPos);
        const atIndex = beforeCursor.lastIndexOf("@");

        if (atIndex >= 0) {
            const query = beforeCursor.slice(atIndex + 1).toLowerCase();
            dropdown.innerHTML = '';

            let filteredMap = {};

            if (query === "") {
                filteredMap = parameterMap;
            } else {
                for (const [category, values] of Object.entries(parameterMap)) {
                    const matched = values.filter(label =>
                        label.toLowerCase().includes(query)
                    );
                    if (matched.length > 0) {
                        filteredMap[category] = matched;
                    }
                }
            }

            if (Object.keys(filteredMap).length > 0) {
                for (const [category, values] of Object.entries(filteredMap)) {
                    const header = document.createElement("div");
                    header.textContent = category;
                    header.style.background = "#eee";
                    header.style.padding = "5px";
                    header.style.fontWeight = "bold";
                    header.setAttribute("data-category", category); // Attach category to header
                    dropdown.appendChild(header);

                    // Create label dropdown for this category
                    const labelDropdown = document.createElement("div");
                    labelDropdown.id = "labelDropdown_" + category;
                    labelDropdown.className = "labelDropdown";
                    labelDropdown.style.display = "none"; // Initially hidden
                    document.body.appendChild(labelDropdown); // Append to body instead of dropdown

                    // Add mouse hover behavior
                    header.addEventListener("mouseenter", () => {
                        labelDropdown.innerHTML = '';  // Clear previous labels
                        values.forEach(label => {
                            const item = document.createElement("div");
                            item.textContent = label;
                            item.onclick = () => {
                                const before = text.slice(0, atIndex);
                                const after = text.slice(cursorPos);
                                prompt.value = before + label + ' ' + after;
                                dropdown.style.display = 'none';
                                labelDropdown.style.display = 'none'; // Also hide label dropdown
                                prompt.focus();
                            };
                            labelDropdown.appendChild(item);
                        });

                        // Show label dropdown next to category
                        const headerRect = header.getBoundingClientRect();
                        const dropdownRect = dropdown.getBoundingClientRect();
                        labelDropdown.style.left = (dropdownRect.right + 5) + "px"; // Position at the right edge of the main dropdown
                        labelDropdown.style.top = headerRect.top + "px";
                        labelDropdown.style.display = "block";
                    });

                    // Hide label dropdown when mouse leaves
                    header.addEventListener("mouseleave", (e) => {
                        // Get the position and dimensions of the label dropdown
                        const labelRect = labelDropdown.getBoundingClientRect();

                        // Check if mouse is moving towards the label dropdown
                        // Add a buffer of 20px to make it more forgiving
                        const buffer = 20;
                        if (e.clientX < labelRect.left && 
                            e.clientY >= (labelRect.top - buffer) && 
                            e.clientY <= (labelRect.bottom + buffer)) {
                            // Mouse is moving towards the label dropdown, don't hide it
                            // Instead, add a mouseover event to the document to track mouse movement
                            const mouseTrackHandler = (moveEvent) => {
                                // If mouse reaches the label dropdown or is very close to it, remove this handler
                                if (moveEvent.clientX >= (labelRect.left - 5) && 
                                    moveEvent.clientX <= (labelRect.right + 5) && 
                                    moveEvent.clientY >= (labelRect.top - buffer) && 
                                    moveEvent.clientY <= (labelRect.bottom + buffer)) {
                                    document.removeEventListener('mousemove', mouseTrackHandler);
                                } 
                                // If mouse moves away from the path to the dropdown, hide it and remove handler
                                else if (moveEvent.clientY < (labelRect.top - buffer) || 
                                         moveEvent.clientY > (labelRect.bottom + buffer) || 
                                         moveEvent.clientX < e.clientX) {
                                    labelDropdown.style.display = "none";
                                    document.removeEventListener('mousemove', mouseTrackHandler);
                                }
                            };

                            document.addEventListener('mousemove', mouseTrackHandler);
                        } else {
                            // Mouse is not moving towards the label dropdown, use the original timeout approach
                            setTimeout(() => {
                                if (!labelDropdown.matches(':hover')) {
                                    labelDropdown.style.display = "none";
                                }
                            }, 300); // Increased timeout for better usability
                        }
                    });

                    // Hide label dropdown when mouse leaves it
                    labelDropdown.addEventListener("mouseleave", () => {
                        labelDropdown.style.display = "none";
                    });
                }

                // Position main dropdown near the cursor
                const rect = prompt.getBoundingClientRect();
                dropdown.style.left = rect.left + "px";
                dropdown.style.top = (rect.top + prompt.offsetHeight) + "px";
                dropdown.style.display = "block";
            } else {
                dropdown.style.display = "none";
            }
        }
    });

    document.addEventListener("click", function (e) {
        // Check if click is outside dropdown and prompt
        if (!dropdown.contains(e.target) && e.target !== prompt) {
            dropdown.style.display = "none";

            // Hide all label dropdowns
            document.querySelectorAll('.labelDropdown').forEach(element => {
                element.style.display = "none";
            });
        }
    });

    // Loading spinner functionality
    document.addEventListener("DOMContentLoaded", function() {
        // Hide spinner when page loads
        const loadingSpinner = document.getElementById("loadingSpinner");
        loadingSpinner.style.display = "none";

        // Show spinner when form is submitted
        const aiForm = document.getElementById("aiForm");
        aiForm.addEventListener("submit", function() {
            loadingSpinner.style.display = "flex";
        });
    });
</script>
</body>
</html>
