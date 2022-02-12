                                                    Pre-Description:
If in the IDE "Run / Debug Configuration" in the "VM Option" key "-Duser.language=eo" was specified,
then command line interface of the program will be printed in Esperanto language.
    When starting the program, the user can specify the parameter "-type" with one of the following options: "json", "xml", or "yaml".
If the parameter is not specified, the program should select the default format(specified in "application.xml") for the file
that is needed to save knowledge tree data when program is finished by user and
to load it with start of a program(if it was previously saved in above-mentioned file).
Name of the file is specified in "application.xml" file.
        Note: files in "resources" folder were recommended to download in description of the last stage of this project at hyperskill.org.
              Some of these files were edited.

                                                      Description:
The computer should greet the user with random greeting or greeting based on the time of the day
(or based on time of the year if it's Christmas season).
    If knowledge (Binary)tree data was saved previously then program should start interacting with user by CLI menu,
in other case program should ask user to enter favorite animal(entered animal will be the root of knowledge tree) and
after that program should interact with user by CLI menu.
        Note: Every time user enters animal(with or without the article), correct article will replace entered article or added.
    After that program offers the user a CLI menu. The menu has to include at least these items:
1. Play the guessing game
2. List of all animals
3. Search for an animal
4. Calculate statistics
5. Print the Knowledge Tree
0. Exit


                                                Description of each menu item:
1) Program should print this message:
       "Let’s play a game!
       You think of an animal, and I guess it.
       Press enter when you’re ready."
     When the computer starts the game, it will ask questions starting from the top of knowledge tree, that is, the root node.
   If current node is not a leaf than program will ask statement question, in other case - program will try to guess the animal.
   The computer must perceive these responses as positive:
   y, yes, yeah, yep, sure, right, affirmative, correct, indeed, you bet, exactly, you said it.
   The negative answer could be:
   n, no, no way, nah, nope, negative, I don't think so, yeah no.     
   The letters can be in any case and there can be a period or an exclamation mark at the end of the statement.
   In case the user's answer is not clear, the program should ask(in different way each time) the user to clarify.
     If the computer makes right guess, program should print random "happy" message and end the game.  
     If the computer makes a wrong guess, it should ask the user three questions:
     1) what animal the user had in mind, and
     2) what statement can help the computer distinguish the animal it guessed (old) from the animal that the person actually thought of (new).
   The program should only accept statements of a certain template: "It can/has/is...".
     3) the program should clarify whether that fact is correct for the new animal.
     After that, the name of the "old" animal in the tree is replaced with the new statement,
   and two new leaves are added to this node: one with the "old" animal and another with the "new" animal, after that the game ends.
     After game ending program should ask user if he/she wants to repeat the game.

   Example:

  Let's look at an example so that you can better visualize the game process.
  When the program starts and prompts the user for their favorite animal, the user replies that it is a cat.

     |
  ( Cat )
    | |
  null null

  We get a binary tree where the root node is unique and has no children.

  Suppose that then the user has thought of a dog. To the computer's question "Is it a cat?", they answer negatively.
  The user then enters the name of the intended animal, "dog", and the statement "It can climb trees".
  The correct answer for a dog is "no". Knowing this, the computer builds a new tree:

  ( Can it climbs trees? )
     |                |
    yes               no
     |                |
  ( Cat )          ( Dog )
    | |              | |
  null null        null null

  In the new tree, the root element is the statement, and the node has two children: the names of the animals.

  So, if the program does not guess the animal correctly, two items are added to the knowledge tree:
  the name of the new animal and a fact that distinguishes one animal from the other.
------------------------------------------------------------------------------------------------------------------------
2) List of all animals:
   The program should traverse the knowledge tree and collect all the animal names into a list.
   The list of animals should be sorted in ascending order without articles.
------------------------------------------------------------------------------------------------------------------------
3) Search for an animal:
   The search has to accept the animal's name with or without the article.
   If the animal is found, the program should print all the facts it knows about this animal.
   If the animal is not found, this information will be printed for the user:
   "No facts about the <animal>."
------------------------------------------------------------------------------------------------------------------------
4) Calculate statistics:
   The program should print:
    - the root node
    - the size of the knowledge tree (number of nodes)
    - the number of animals, the number of facts
    - and the maximum and minimum depth of the tree which correspond to the minimum and maximum number of questions the program gets before it wins or gives up.
------------------------------------------------------------------------------------------------------------------------
5) Print the Knowledge Tree
   The program should print the Knowledge Tree.
------------------------------------------------------------------------------------------------------------------------
0) Exit:
   The knowledge tree data should be serialized and program should be terminated.