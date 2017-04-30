#extension GL_OES_standard_derivatives : enable
precision mediump float;

uniform vec3                iResolution;
uniform vec3                iChannelResolution;
uniform float               iGlobalTime;
uniform sampler2D           iChannel0;
varying vec2                texCoord;


vec4 blackwhite(in vec4 col){

    vec3 c_r = vec3(0.3, 0.6, 0.1);
	vec3 c_g = vec3(0.3, 0.6, 0.1);
	vec3 c_b = vec3(0.3, 0.6, 0.1);

	vec3 rgb = vec3( dot(col.rgb,c_r), dot(col.rgb,c_g), dot(col.rgb,c_b) );
    return vec4(rgb, 1.);
}


void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
	vec2 uv = fragCoord.xy / iResolution.xy;
    vec2 xy = vec2(uv.x,uv.y);

	float clarity = (-2.);

    vec3 col1 = texture2D( iChannel0, xy).rgb;
    vec3 col2 = texture2D( iChannel0, xy, abs(clarity)).rgb;

    vec4 col = vec4( col1 * 2.0 - col2, 1.0 ); // -clarity

	fragColor =  blackwhite(col);
}

void main() {
    mainImage(gl_FragColor, texCoord.xy*iResolution.xy);
}
