%a function that simulates the socialization at a house with S seniors, J
%juniors, P sophomores and F first years. At each year, each person makes
%friend with one other person in the house t times
function[G] = simulateHouse(S,J,P,F,t,method)
%method represents the way of socialization: could be "distance," "richer,"
%"classyear"

size = S;
G = zeros(S,S);  %start with G with S nodes and 0 edges
G = socialize(G,t,method);     %let the S nodes socialize t times

G = [G; zeros(J,size)]; 
size = size + J;
G = [G(:,1:S) zeros(size,J)];    %add the extra J nodes to the graph G
G = socialize(G,t,method);     %let the S+J nodes socialize t times

G = [G; zeros(P,size)];     
size = size + P;
G = [G(:,1:S+J) zeros(size,P)];   %add the extra P nodes to the graph G
G = socialize(G,t,method);     %let the S+J+P nodes socialize t times

G = [G; zeros(F,size)]; 
size = size + F;
G = [G(:,1:S+J+P) zeros(size,F)];   %add the extra F nodes to the graph G
G = socialize(G,t,method);     %let the S+J+P+F nodes socialize t times

