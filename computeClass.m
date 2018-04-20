function [classyear] = computeClass(node,S,J,P,F)

classyear = 0;

if(node > 0 && node <= S)
    classyear = 4;    %4 represents senior
elseif(node > S && node <= (S+J))
    classyear = 3;    %3 represents junior
elseif(node > (S+J) && node <= (S+J+P))
    classyear = 2;     %2 represents sophomore
elseif(node > (S+J+P) && node <= (S+J+P+F))
    classyear = 1;     %1 represents first year
end