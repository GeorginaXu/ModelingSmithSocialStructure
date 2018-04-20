%A function that output the class year of a node given the number of
%students in each class year
function [classyear] = computeClass(node,S,J,P,F)

%initialize the class year of node to 0
classyear = 0;

%if node is in range S, it is a senior
if(node > 0 && node <= S)
    classyear = 4;    %4 represents senior
    
%check if node is a junior
elseif(node > S && node <= (S+J))
    classyear = 3;    %3 represents junior
    
%check if node is a sophomore
elseif(node > (S+J) && node <= (S+J+P))
    classyear = 2;     %2 represents sophomore
    
%check if node is a first year
elseif(node > (S+J+P) && node <= (S+J+P+F))
    classyear = 1;     %1 represents first year
end