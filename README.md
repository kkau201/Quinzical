# Quinzical (SE206-a3)
Quinzical is a quiz game designed for kids aged 8-12 to learn and test their knowledge on New Zealand. 
<ul>
    <li>This involves a 'Play' module where 5 random categories will be chosen, each containing 5 clues. A clue will have a monetary value assigned and any correct answers will reward the user by increasing the score. 
    After two categories have been completed, there will also be a bonus international category.</li>
    <li>This also features a 'Practice' module, where the user may choose from all available categories. They will be directed to a randomly chosen clue and have three attempts to answer. No points are recorded.</li>
</ul>

##Installation
The directory MUST contain the following provided files and folders: 
<ul>
    <li> Quinzical.jar </li>
    <li> Quinzical.sh </li>
    <li> "categories" folder </li>
    <li> "international" folder </li>
</ul>

Once everything is in the same directory(folder), open terminal and ensure the path 
is of that directory

Run the command: ```./Quinzical.sh```

If not able to run, enter ```chmod +x Quinzical.sh```, then run command ```./Quinzical.sh```

The terminal will ask for a password, this is because it needs to move the voice pack into system directory. 
Please enter the password in the terminal.

Also recommend installing festlex-oald by running the command:
```sudo apt-get install festlex-oald```

##Contributing
If you wish to add your own categories, it must be a .txt file under the folder "categories". The layout must follow;
<ul>
    <li>The file name must be the title of the category i.e. Famous People</li>
    <li>Each line of the file is a single clue to play and must have at least 5 clues</li>
    <li>A single line must be laid out like this "Clue; (Starter Text) Answer" </li>
    <li>A 'clue' is what the user will be asked i.e. "This New Zealander was the first person to climb Mount Everest;"</li>
    <li>The 'starter text' is how a question would begin i.e. "(Who is)"</li>
    <li>A 'answer' is what is expected from the user to input i.e. "Edmund Hillary"</li>
    <li>If there is multiple forms of the answer they may respond with, separate with a slash ' / ' <br>i.e "Edmund Hillary/Sir Edmund Hillary"</li>
    <li>If there are multiple parts of the answers separate with a comma ' , ' <br>i.e. "These are the colours on the New Zealand Flag; (What are) Red,White,Blue" </li>
</ul>

WARNING: Failure to follow this guideline, will result in faults.

##Authors
Wayne Yao & Kayla Kautai

##Attributions
<div><a href="https://www.uistore.design/items/80-streamline-illustrations/"> Doodle illustrations</a> created by Webalys</div>
<div><a href="https://www.freepik.com/vectors/business">Coin vector</a> created by rawpixel.com - www.freepik.com</div>
<div>Home icon made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
<div>Speaker icon made by <a href="https://www.flaticon.com/authors/pixel-perfect" title="Pixel perfect">Pixel perfect</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
<div>Plus icon made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon"> www.flaticon.com</a></div>
<div>Minus icon made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon"> www.flaticon.com</a></div>
<div>All international images made by <a href='https://www.freepik.com/vectors/travel'>Freepik</a></div>
