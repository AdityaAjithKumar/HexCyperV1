document.addEventListener("DOMContentLoaded", function() {
  var inputField = document.querySelector('input[type="text"]');
  inputField.addEventListener("keydown", function(event) {
      console.log("Key pressed:", event.key); // Debugging: Log the pressed key
      if (event.key === "Enter") {
          console.log("Enter key pressed"); // Debugging: Log that Enter key is pressed
          event.preventDefault(); // Prevent default behavior of Enter key (submitting form)
          this.value += "\n"; // Add newline character to input value
          console.log("Input value after Enter:", this.value); // Debugging: Log the updated input value
      }
  });
});

function displaySentence() {
  // Get the value of the input field
  var sentence = document.getElementById("sentenceInput").value;

  // Display the sentence on the page
  document.getElementById("output").innerText = "You entered: " + sentence;
}




$(document).ready(function() {
    $('ul li span').each(function() {
        var jsonResponse = $(this).text();
        try {
            var jsonObject = JSON.parse(jsonResponse);
            var content = jsonObject.content;
            // Use .html() instead of .text()
            $(this).html(content);
        } catch (e) {
            console.error("Parsing error:", e);
        }
    });
});
