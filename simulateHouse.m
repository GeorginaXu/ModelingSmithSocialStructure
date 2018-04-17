function[G] = simulateHouse(S,J,P,F)

size = S;
G = zeros(S,S);  %start with G with S nodes and 0 edges
G = socialize(G,3);     %let the S nodes socialize 10 times

G = [G; zeros(J,size)]; 
size = size + J;
G = [G(:,1:S) zeros(size,J)];    %add the extra J nodes to the graph G
G = socialize(G,3);     %let the S+J nodes socialize 10 times

G = [G; zeros(P,size)];     
size = size + P;
G = [G(:,1:S+J) zeros(size,P)];   %add the extra P nodes to the graph G
G = socialize(G,3);     %let the S+J+P nodes socialize 10 times

G = [G; zeros(F,size)]; 
size = size + F;
G = [G(:,1:S+J+P) zeros(size,F)];   %add the extra F nodes to the graph G
G = socialize(G,3);     %let the S+J+P+F nodes socialize 10 times

