function[prob_matrix] = computeProbability(G,x)

distance_matrix = computeDistance(G,x);
prob_matrix = [];

for d = 1:size(distance_matrix,1)
    if(distance_matrix(1,d) == 0)
        prob_matrix = [prob_matrix,0];
    else
        p = 1/d;
        prob_matrix = [prob_matrix, p];
    end
end